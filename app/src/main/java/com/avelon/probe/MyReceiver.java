package com.avelon.probe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();

    public MyReceiver(Context ctx) {
        for(Field field : Intent.class.getDeclaredFields()) {
            String name = field.getName();
            if(name.startsWith("ACTION_")) {
                Log.i(TAG, "Register android.intent.action." + name);
                ctx.registerReceiver(this, new IntentFilter("android.intent.action." + name));
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive() = " + intent);

        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.e(TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }
}
