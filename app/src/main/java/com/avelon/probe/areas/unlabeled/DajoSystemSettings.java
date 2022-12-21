package com.avelon.probe.areas.unlabeled;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.Random;

public class DajoSystemSettings extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public DajoSystemSettings(Context ctx) throws Exception {
        super(DajoSystemSettings.class, ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "autotime: " + Settings.System.getString(ctx.getContentResolver(), Settings.System.AUTO_TIME));
        Settings.System.putInt(ctx.getContentResolver(), Settings.System.AUTO_TIME, 0);
        Log.i(TAG, "autotime: " + Settings.System.getString(ctx.getContentResolver(), Settings.System.AUTO_TIME));


        // Screen brightness
        Random rand = new Random();
        Thread t = new Thread(() -> {
            while(true) {
                int val = rand.nextInt(255);
                //Log.e(TAG, "run(): " + val);
                Settings.System.putInt(ctx.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, val);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
