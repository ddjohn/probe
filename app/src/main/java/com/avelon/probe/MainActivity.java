package com.avelon.probe;

import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Telephony;
import android.service.voice.VoiceInteractionService;
import android.util.Log;
import android.widget.Toast;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.DajoAlertDialog;
import com.avelon.probe.areas.DajoTextToSpeech;
import com.avelon.probe.areas.managers.DajoProjectionManager;
import com.avelon.probe.areas.lifecycle.MyActivityLifecycle;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

        for(FeatureInfo feature : manager.getSystemAvailableFeatures()) {
            Log.i(TAG, "feature: " + feature.name);
        }
        for(PackageInfo pkg : manager.getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA | PackageManager.GET_INTENT_FILTERS)) {
            Log.i(TAG, "package: " + pkg.packageName);
            Log.i(TAG, "user: " + pkg.sharedUserId);
            Log.i(TAG, "version: " + pkg.versionName);
            for(ActivityInfo activity : (pkg.activities != null ? pkg.activities : new ActivityInfo[] {})) {
                Log.i(TAG, "activity: " + activity);
            }
        }
        for(ApplicationInfo application : manager.getInstalledApplications(0)) {
            Log.e(TAG, "application: " + application);
            if (manager.getLaunchIntentForPackage(application.packageName) != null) {
                Log.e(TAG, "action: " + manager.getLaunchIntentForPackage(application.packageName).getAction());
                Log.e(TAG, "type: " + manager.getLaunchIntentForPackage(application.packageName).getType());
                Log.e(TAG, "identifier: " + manager.getLaunchIntentForPackage(application.packageName).getIdentifier());
                Log.e(TAG, "schema: " + manager.getLaunchIntentForPackage(application.packageName).getScheme());
            }
        }


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
        ArrayList<String> actions = new ArrayList<>();
        for(Field field : Intent.class.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);

            if(name.startsWith("ACTION_")) {
                Class<?> targetType = field.getType();
                Object objectValue = targetType.newInstance();

                actions.add((String)field.get(objectValue));
            }
        }

        for(String action : actions.toArray(new String[0])) {
            Intent intent = new Intent(action, null);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if(resolveInfo != null)
            Log.e(TAG, "---> " + action + ":" + resolveInfo.activityInfo.name);

            List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
            if(apps.size() > 0) {
                Log.e(TAG, "+++> " + action + ":" + apps.get(0).activityInfo.name);
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
            Log.d(TAG,"======packet default:==="+activity.getPackageName());
        }
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
        }
  */
    }
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
}
catch(Exception e) {
    Log.e(TAG, "error", e);
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

        // Receivers
        MyReceiver receiver = new MyReceiver(this);

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