package com.avelon.probe;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MyActivityLifecycle extends Activity {
    private static final String TAG = MyActivityLifecycle.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy()");
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        Log.e(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop()");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart()");
        super.onRestart();
    }
}
