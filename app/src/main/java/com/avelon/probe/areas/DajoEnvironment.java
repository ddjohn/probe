package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class DajoEnvironment extends AbstractManager {
    public static String[] permissions = new String[] {};

    public DajoEnvironment(Context ctx) throws Exception {
        super(ctx, permissions);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.e(TAG, "dir=" + Environment.getExternalStorageDirectory());
        Log.e(TAG, "dir=" + Environment.getExternalStorageState());
        Log.e(TAG, "dir=" + Environment.DIRECTORY_DOCUMENTS);
        //Log.e(TAG, "dir=" + Environment.getStorageDirectory());
        Log.e(TAG, "dir=" + Environment.getRootDirectory());
        Log.e(TAG, "dir=" + Environment.getDownloadCacheDirectory());
        Log.e(TAG, "dir=" + Environment.getDataDirectory());
    }
}
