package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoStorageManager extends AbstractManager {
    private StorageManager manager;
    public static String[] permissions = new String[] {};

    public DajoStorageManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (StorageManager)ctx.getSystemService(Context.STORAGE_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "primary=" + manager.getPrimaryStorageVolume());
        //for(StorageVolume volume : storage.getRecentStorageVolumes()) {
        //    Log.e(TAG, "volume=" + volume);
        //}
    }
}
