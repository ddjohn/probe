package com.avelon.probe.areas.concepts;

import android.annotation.SuppressLint;
import android.content.Context;
import com.avelon.probe.areas.AbstractManager;

public class MutualTls extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public MutualTls(Context ctx) throws Exception {
        super(ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {

    }
}