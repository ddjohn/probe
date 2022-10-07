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

    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "deviceMake=" + Build.MANUFACTURER);
        Log.i(TAG, "deviceName=" + Build.DEVICE);
        Log.i(TAG, "deviceName=" + Build.MODEL);
        Log.i(TAG, "deviceName=" + Build.BOARD);
        Log.i(TAG, "deviceName=" + Build.BOOTLOADER);
        Log.i(TAG, "deviceName=" + Build.BRAND);
        Log.i(TAG, "deviceName=" + Build.DISPLAY);
        Log.i(TAG, "deviceName=" + Build.FINGERPRINT);
        Log.i(TAG, "deviceName=" + Build.HARDWARE);
        Log.i(TAG, "deviceName=" + Build.HOST);
        Log.i(TAG, "deviceName=" + Build.ID);
        //Log.e(TAG, "deviceName=" + Build.ODM_SKU);
        Log.i(TAG, "deviceName=" + Build.PRODUCT);
        //Log.e(TAG, "deviceName=" + Build.SOC_MANUFACTURER);
        //Log.e(TAG, "deviceName=" + Build.SOC_MODEL);
        Log.i(TAG, "deviceName=" + Build.TAGS);
        Log.i(TAG, "deviceName=" + Build.TYPE);
        Log.i(TAG, "deviceName=" + Build.USER);
        Log.i(TAG, "version=Android " + Build.VERSION.RELEASE);

        //Log.e(TAG, "serial=" + Build.getSerial());
    }
}
