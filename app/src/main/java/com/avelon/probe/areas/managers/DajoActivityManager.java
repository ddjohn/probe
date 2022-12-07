package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoActivityManager extends AbstractManager {
    private ActivityManager manager;
    public static String[] permissions = new String[] {};

    public DajoActivityManager(Context ctx) throws Exception {
        super(DajoActivityManager.class, ctx, permissions);

        manager = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        for(ActivityManager.AppTask task : manager.getAppTasks()) {
            Log.i(TAG, "task=" + task.getTaskInfo());
        }
        manager.getRecentTasks(10, 0).forEach(task -> {
            Log.i(TAG, "recent=" + task.baseIntent.getComponent());
        });

        //Log.e(TAG, "top application: " + activityManager.getRunningAppProcesses().get(0).processName);
        //Log.e(TAG, "top tasks: " + activityManager.getAppTasks().get(0).getTaskInfo().baseActivity);
        //Log.e(TAG, "top tasks: " + activityManager.getAppTasks().get(0).getTaskInfo().origActivity);

        //activityManager.getAppTasks().forEach(task -> Log.e(TAG, "task=" + task));

    }
}
