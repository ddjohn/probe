package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.util.Log;

public class DajoCompanionDeviceManager extends AbstractManager {
    private CompanionDeviceManager manager;
    public static String[] permissions = new String[] {};

    public DajoCompanionDeviceManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (CompanionDeviceManager)ctx.getSystemService(Context.COMPANION_DEVICE_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.getAssociations().forEach(phone -> {
            Log.e(TAG, "phone=" + phone);
        });
    }
}
