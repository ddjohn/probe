package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.TemplateManager;

public class DajoAccessibilityManager extends AbstractManager implements AccessibilityManager.TouchExplorationStateChangeListener {
    private AccessibilityManager manager;
    public static String[] permissions = new String[] {};

    public DajoAccessibilityManager(Context ctx) throws Exception {
        super(ctx, permissions);

        /* Manager */
        manager = (AccessibilityManager)ctx.getSystemService(Context.ACCESSIBILITY_SERVICE);

        /* Listener */
        manager.addTouchExplorationStateChangeListener(this);
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

    @Override
    public void onTouchExplorationStateChanged(boolean b) {
        Log.e(TAG, "cb:onTouchExplorationStateChanged(): " + b);
    }
}
