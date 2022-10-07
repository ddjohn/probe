package com.avelon.probe.areas;

import android.content.Context;
//import android.os.SystemProperties;
import android.util.Log;

public class DajoSystemProperties extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public DajoSystemProperties(Context ctx) throws Exception {
        super(ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
       // Log.e(TAG, "" + SystemProperties.get("ro.build.user"));
    }
}
