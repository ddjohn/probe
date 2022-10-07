package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;

public class TemplateManager extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public TemplateManager(Context ctx) throws Exception {
        super(ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {

    }
}
