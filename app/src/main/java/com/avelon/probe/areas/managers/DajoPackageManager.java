package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.List;

public class DajoPackageManager extends AbstractManager {
    private PackageManager manager;
    public static String[] permissions = new String[] {};

    public DajoPackageManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = ctx.getPackageManager();
    }

    @Override
    public void orchestrate() throws Exception {
        List<ApplicationInfo> packages = manager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo info : packages) {
            Log.i(TAG, "info=" + info);
            Log.i(TAG, "info=" + manager.getLaunchIntentForPackage(info.className));
        }
        //packageManager.deletePackage("com.aptiv.got.testfullframework", null, 0);
        //packageManager.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
    }
}
