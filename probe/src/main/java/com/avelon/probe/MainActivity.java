package com.avelon.probe;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.services.MyAccessibilityService;
import com.avelon.probe.areas.services.MyMediaService;
import com.avelon.probe.areas.services.MyService;
import com.avelon.probe.areas.unlabeled.DajoAlertDialog;
import com.avelon.probe.areas.unlabeled.DajoTextToSpeech;
import com.avelon.probe.areas.managers.DajoProjectionManager;
import com.avelon.probe.areas.lifecycle.MyActivityLifecycle;

import java.util.List;

public class MainActivity extends MyActivityLifecycle {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyPermissions permissions;
    private DajoProjectionManager projection;
    private DajoTextToSpeech tts;

    private boolean test = false;

    MediaBrowser mediaBrowser = null;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checking Permissions
        permissions = new MyPermissions(this);
        permissions.request();
        if(permissions.check() == false) {
            Log.e(TAG, "Missing permission - skipping!");
            //return;
        }

        //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //startActivityForResult(intent, 9090);

        // Receivers
        MyReceiver receiver = new MyReceiver(this);

        if(test) return;

        // Starting Services
        //startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),828);
        startService(new Intent(this, MyAccessibilityService.class));
        startService(new Intent(this, MyService.class));
        startService(new Intent(this, DajoMediaBrowser1.class));
        startService(new Intent(this, DajoMediaBrowser2.class));

        // Crash button
        Button crashButton = new Button(this);
        crashButton.setText("Test Crash");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });
        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        mediaBrowser = new MediaBrowser(this,
                new ComponentName(this, MyMediaService.class), new MediaBrowser.ConnectionCallback() {
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

        if(test) return;

        try {
            AbstractManager[] managers = {
                    new DajoAlertDialog(this),
                    tts = new DajoTextToSpeech(this),
                    projection = new DajoProjectionManager(this),
            };
            for (AbstractManager manager : managers) {
                Log.e(TAG, "=== Activity: " + manager.getClass().getSimpleName() + " ===");
                try {
                    manager.orchestrate();
                }
                catch(Exception e) {
                    Log.e(TAG, "exception", e);
                }
            }
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }

        // Concepts
        Log.e(TAG, "=== Concepts ===");
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
                Log.e(TAG, "Unknown request data: " + data);
            }
        }
    }
}