package com.avelon.probe.areas.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

public abstract class MyServiceLifecycle extends Service {
    private static final String TAG = MyServiceLifecycle.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
}
