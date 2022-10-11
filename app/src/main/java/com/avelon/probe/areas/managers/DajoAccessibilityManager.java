package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.TemplateManager;

public class DajoAccessibilityManager extends AbstractManager {
    private AccessibilityManager manager;
    public static String[] permissions = new String[] {};

    public DajoAccessibilityManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (AccessibilityManager)ctx.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.getEnabledAccessibilityServiceList(AccessibilityManager.FLAG_CONTENT_TEXT).forEach(service -> {
            Log.e(TAG, "service=" + service);
        });
        manager.getInstalledAccessibilityServiceList().forEach(instance -> {
            Log.e(TAG, "instance=" + instance);
        });
    }
}
