package com.avelon.probe;

import android.Manifest;
import android.app.Activity;
import android.car.Car;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class MyPermissions {
    private static final String TAG = MyPermissions.class.getSimpleName();
    public static final int REQUEST_CODE = 666;
    private static String[] permissions = new String[] {
            //Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_LOGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //Manifest.permission.WRITE_SETTINGS,
            Car.PERMISSION_VENDOR_EXTENSION,
    };

    private Activity activity;

    public MyPermissions(Activity activity) {
        this.activity = activity;
    }

    public void request() {
        Log.i(TAG, "request()");
        activity.requestPermissions(permissions, REQUEST_CODE);
    }

    public boolean check() {
        Log.i(TAG, "check()");
        for(String permission: permissions) {
            Log.i(TAG, "Checking permission: " + permission);
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission not approved: " + permission);
                return false;
            }
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()");
    }
}
