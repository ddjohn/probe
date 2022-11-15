package com.avelon.probe.areas.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    public MyAccessibilityService() {
        Log.e(TAG, "MyAccessibilityService()");

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "cb:onAccessibilityEvent(): " + accessibilityEvent);
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "cb:onInterrupt()");
    }
}
