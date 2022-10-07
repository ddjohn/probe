package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class DajoBuild extends AbstractManager {
    public static String[] permissions = new String[] {};

    public DajoBuild(Context ctx) throws Exception {
        super(ctx, permissions);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.e(TAG, "deviceMake=" + Build.MANUFACTURER);
        Log.e(TAG, "deviceName=" + Build.DEVICE);
        Log.e(TAG, "deviceName=" + Build.MODEL);
        Log.e(TAG, "deviceName=" + Build.BOARD);
        Log.e(TAG, "deviceName=" + Build.BOOTLOADER);
        Log.e(TAG, "deviceName=" + Build.BRAND);
        Log.e(TAG, "deviceName=" + Build.DISPLAY);
        Log.e(TAG, "deviceName=" + Build.FINGERPRINT);
        Log.e(TAG, "deviceName=" + Build.HARDWARE);
        Log.e(TAG, "deviceName=" + Build.HOST);
        Log.e(TAG, "deviceName=" + Build.ID);
//        Log.e(TAG, "deviceName=" + Build.ODM_SKU);
        Log.e(TAG, "deviceName=" + Build.PRODUCT);
//        Log.e(TAG, "deviceName=" + Build.SKU);
//        Log.e(TAG, "deviceName=" + Build.SOC_MANUFACTURER);
//        Log.e(TAG, "deviceName=" + Build.SOC_MODEL);
        Log.e(TAG, "deviceName=" + Build.TAGS);
        Log.e(TAG, "deviceName=" + Build.TYPE);
        Log.e(TAG, "deviceName=" + Build.USER);
        Log.e(TAG, "version=Android " + Build.VERSION.RELEASE);
    }
}
