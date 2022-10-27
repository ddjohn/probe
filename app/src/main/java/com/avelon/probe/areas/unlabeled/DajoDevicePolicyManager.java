package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoDevicePolicyManager extends AbstractManager {
    private DevicePolicyManager manager;
    public static String[] permissions = new String[] {};

    public DajoDevicePolicyManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.e(TAG, "Admins:");
        for(ComponentName name : manager.getActiveAdmins()) {
            Log.e(TAG, "name=" + name);
        }
        manager.lockNow();
        //devicePolicyManager.setOrganizationId("Aptiv");
        //devicePolicyManager.setOrganizationName("", "Aptiv");
        //Log.e(TAG, "failed logins=" + devicePolicyManager.getCurrentFailedPasswordAttempts());
        //devicePolicyManager.
        //Log.e(TAG, "isAdmin=" + manager.isAdminActive(componentName));
    }
}
