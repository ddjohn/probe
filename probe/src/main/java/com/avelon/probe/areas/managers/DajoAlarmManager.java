package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoAlarmManager extends AbstractManager {
    private AlarmManager manager;
    public static String[] permissions = new String[] {};

    public DajoAlarmManager(Context ctx) throws Exception {
        super(DajoAlarmManager.class, ctx, permissions);

        manager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "alarm=" + manager.getNextAlarmClock());
    }
}
