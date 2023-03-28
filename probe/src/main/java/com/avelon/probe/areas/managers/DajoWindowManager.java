package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.avelon.probe.areas.AbstractManager;

public class DajoWindowManager extends AbstractManager {
    private WindowManager manager;
    public static String[] permissions = new String[] {};

    public DajoWindowManager(Context ctx) throws Exception {
        super(DajoWindowManager.class, ctx, permissions);

        manager = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        DisplayMetrics dm = new DisplayMetrics();
        Log.i(TAG,"deviceScreenSize: " + dm.widthPixels + " x " + dm.heightPixels);

        manager.getDefaultDisplay().getMetrics(dm);
        double x = dm.widthPixels/dm.xdpi;
        double y = dm.heightPixels/dm.ydpi;
        Log.i(TAG,"deviceScreenSize: " + x + " x " + y);
    }
}
