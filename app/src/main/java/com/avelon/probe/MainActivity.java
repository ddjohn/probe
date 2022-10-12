package com.avelon.probe;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.DajoAlertDialog;
import com.avelon.probe.areas.DajoTextToSpeech;
import com.avelon.probe.areas.managers.DajoProjectionManager;
import com.avelon.probe.areas.lifecycle.MyActivityLifecycle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyActivityLifecycle {
    private static final String TAG = MainActivity.class.getCanonicalName();

    private MyPermissions permissions;
    private DajoProjectionManager projection;
    private DajoTextToSpeech tts;

    MediaBrowser mediaBrowser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Stage area
        PackageManager manager = (PackageManager)this.getPackageManager();
        Log.e(TAG, "");

    try {
        //Default Dial App
        {
            Intent mainIntent = new Intent(Intent.ACTION_DIAL, null);
            mainIntent.addCategory(Intent.CATEGORY_DEFAULT);
            List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
            Log.e(TAG, "dial: " + pkgAppsList.size());

            ActivityInfo info = pkgAppsList.get(0).activityInfo;
            Log.e(TAG, "dial: " + info);
        }
        {
            Intent intent = new Intent(Intent.ACTION_ASSIST, null);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            ResolveInfo info = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (info != null) {
                Log.e(TAG, "DAJO package: " + info.activityInfo.name);
            }
        }
        {
            Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND, null);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            ResolveInfo info = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (info != null) {
                Log.e(TAG, "DAJO package: " + info.activityInfo.name);
            }
        }

        IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);
        List<ComponentName> activities = new ArrayList<>();
        final PackageManager packageManager = (PackageManager) getPackageManager();

        packageManager.getPreferredActivities(filters, activities, null);
        for (ComponentName activity : activities) {
            Log.d(TAG, "======packet default:===" + activity.getPackageName());
        }

        //com.android.internal.app.AssistUtils utils = new com.android.internal.app.AssistUtils(this);
        //ComponentName name = utils.getAssistComponentForUser(10);
        //Log.e(TAG, "name: " + name);
        /*
        try {
            Method myUserIdMethod = UserHandle.class.getDeclaredMethod("myUserId");
            myUserIdMethod.setAccessible(true);
            Integer userId = (Integer) myUserIdMethod.invoke(null);

            if (userId != null) {
                Constructor constructor = Class.forName("com.android.internal.app.AssistUtils").getConstructor(Context.class);
                Object assistUtils = constructor.newInstance(this);

                Method getAssistComponentForUserMethod = assistUtils.getClass().getDeclaredMethod("getAssistComponentForUser", int.class);
                getAssistComponentForUserMethod.setAccessible(true);
                return (ComponentName) getAssistComponentForUserMethod.invoke(assistUtils, userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //Default SMS App
        {
            //String smsPkgName = Telephony.Sms.getDefaultSmsPackage(this);
            //ApplicationInfo info = getPackageManager().getApplicationInfo(smsPkgName, 0);
            //Log.e(TAG, "sms: " + info);
        }

        //Default Browser App
        {
            //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"));
            //ResolveInfo resolveInfo = getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
            //ActivityInfo info = resolveInfo.activityInfo;
            //Log.e(TAG, "browser: " + info);
        }

        //Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show();
        //Snackbar.make(this, this.getCurrentFocus(), "Snackbar", BaseTransientBottomBar.LENGTH_LONG).show();

/*
        Intent intent = new Intent(Intent.ACTION_MANAGED_DEFAULT_APP);
        intent.setPackage(this.getPackageName());
        intent.putExtra(Intent.EXTRA_ROLE_NAME, RoleManager.ROLE_ASSISTANT);
        this.startActivity(intent);
*/
        //VoiceInteractionServiceInfo voiceInfo = new VoiceInteractionServiceInfo(pm, resolveInfo.serviceInfo);

        if(1==1)
            return;

        // Checking Permissions
        permissions = new MyPermissions(this);
        permissions.request();
        if(permissions.check() == false) {
            Log.e(TAG, "Missing permission - skipping!");
            return;
        }

        // Receivers
        MyReceiver receiver = new MyReceiver(this);

        // Starting Services
        startService(new Intent(this, MyService.class));

        mediaBrowser = new MediaBrowser(this,
                new ComponentName(this, MyMediaService.class),
                new MediaBrowser.ConnectionCallback() {
                    @Override
                    public void onConnected() {
                        super.onConnected();

                        MediaSession.Token token = mediaBrowser.getSessionToken();
                        Log.e(TAG, "token=" + token);

                        MediaController mediaController = new MediaController(MainActivity.this, token);
                        Log.e(TAG, "controller=" + mediaController);

                        String root = mediaBrowser.getRoot();
                        Log.i(TAG, "MediaRoot=" + root);
                    }
                },
                null);
        Log.e(TAG, "browser=" + mediaBrowser);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            AbstractManager[] managers = {
                    new DajoAlertDialog(this),
                    tts = new DajoTextToSpeech(this),
                    projection = new DajoProjectionManager(this),
            };
            for (AbstractManager manager : managers) {
                Log.e(TAG, "=== " + manager.getClass().getSimpleName() + " ===");
                manager.orchestrate();
            }
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }

        // Concepts
        MyConcepts concepts = new MyConcepts(this);
        concepts.init();

        Log.e(TAG, "connect to browsing service");
//        mediaBrowser.connect();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult(): " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case MyPermissions.REQUEST_CODE: {
                permissions.onActivityResult(requestCode, resultCode, data);
                break;
            }
            case DajoProjectionManager.REQUEST_CODE: {
                projection.onActivityResult(requestCode, resultCode, data);
                break;
            }
            case DajoTextToSpeech.REQUEST_CODE: {
                tts.onActivityResult(requestCode, resultCode, data);
                break;
            }
            default: {
                Log.e(TAG, "Unknown request code: " + requestCode);
            }
        }
    }
}