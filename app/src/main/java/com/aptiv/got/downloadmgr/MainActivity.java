package com.aptiv.got.downloadmgr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    MediaBrowser mediaBrowser = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MyService.class));

        if (requestPermissions()) return;

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
        Log.e(TAG, "connect to browsing service");
        mediaBrowser.connect();
    }

    private boolean requestPermissions() {
        String permissions[] = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        this.requestPermissions(permissions, 666);

        for(String permission : permissions) {
            Log.e(TAG, "Checking permission " + permission);
            if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "No permission for: " + permission);
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "code=" + resultCode);
        Log.e(TAG, "result=" + resultCode);
    }
}