package com.avelon.probe;

import android.app.Service;
import android.content.Intent;
import android.util.Log;

public abstract class MyServiceLifecycle extends Service {
    private static final String TAG = MyServiceLifecycle.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
}
