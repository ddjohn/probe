package com.avelon.probe.areas.unlabeled;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoSecureSettings extends AbstractManager {
    //private TemplateManager manager;
    public static String[] permissions = new String[] {};

    public DajoSecureSettings(Context ctx) throws Exception {
        super(DajoSecureSettings.class, ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        String id = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e(TAG, "deviceId=" + id);
    }
}
