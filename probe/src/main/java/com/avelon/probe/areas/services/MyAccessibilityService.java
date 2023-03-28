package com.avelon.probe.areas.services;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    public MyAccessibilityService() {
        Log.e(TAG, "MyAccessibilityService()");


    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        super.onCreate();

        checkCallingOrSelfPermission(Manifest.permission.BIND_ACCESSIBILITY_SERVICE);

        AccessibilityManager manager = (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);
        for(AccessibilityServiceInfo info : manager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)) {
            Log.e(TAG, "info=" + info.eventTypes);
            Log.e(TAG, "info=" + info.feedbackType);
            Log.e(TAG, "info=" + info.packageNames);
            Log.e(TAG, "info=" + info.flags);
            Log.e(TAG, "info=" + info.getId());
            Log.e(TAG, "info=" + info.getSettingsActivityName());
            Log.e(TAG, "info=" + info);
        }
    }

    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "onServiceConnected()");
        super.onServiceConnected();

        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED;
        info.packageNames = new String[]
                {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "cb:onAccessibilityEvent(): " + accessibilityEvent);
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "cb:onInterrupt()");
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.e(TAG, "unbindService()");
        super.unbindService(conn);
    }
}
