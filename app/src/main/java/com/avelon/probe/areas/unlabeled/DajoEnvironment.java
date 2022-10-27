package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoEnvironment extends AbstractManager {
    public static String[] permissions = new String[] {};

    public DajoEnvironment(Context ctx) throws Exception {
        super(ctx, permissions);
    }

    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "dir=" + Environment.getExternalStorageDirectory());
        Log.i(TAG, "dir=" + Environment.getExternalStorageState());
        Log.i(TAG, "dir=" + Environment.DIRECTORY_DOCUMENTS);
        //Log.e(TAG, "dir=" + Environment.getStorageDirectory());
        Log.i(TAG, "dir=" + Environment.getRootDirectory());
        Log.i(TAG, "dir=" + Environment.getDownloadCacheDirectory());
        Log.i(TAG, "dir=" + Environment.getDataDirectory());
    }
}
