package com.avelon.probe.areas;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class DajoSystemSettings extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public DajoSystemSettings(Context ctx) throws Exception {
        super(ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "autotime: " + Settings.System.getString(ctx.getContentResolver(), Settings.System.AUTO_TIME));
        Settings.System.putInt(ctx.getContentResolver(), Settings.System.AUTO_TIME, 0);
        Log.i(TAG, "autotime: " + Settings.System.getString(ctx.getContentResolver(), Settings.System.AUTO_TIME));

    }
}
