package com.avelon.probe;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    public MyAccessibilityService() {
        Log.e(TAG, "MyAccessibilityService()");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "onAccessibilityEvent()");
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt()");
    }
}
