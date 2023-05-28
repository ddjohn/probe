package com.avelon.probe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.Field;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();

    public MyReceiver() {
        Log.e(TAG, "MyReceiver()");
    }

    public MyReceiver(Context ctx) {
        Log.e(TAG, "MyReceiver(ctx)");
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

        if("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
            Log.e(TAG, "A phone call: " + intent.getExtras().getString("incoming_number"));
            Bundle bundle = intent.getExtras();
            for (String key: bundle.keySet()) {
                Log.e(TAG, "extra: " + key + " - " + intent.getExtras().get(key));
            }
            //Log.e(TAG, "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME));

            Toast.makeText(context, "A phone call!", Toast.LENGTH_LONG).show();
        }
    }
}
