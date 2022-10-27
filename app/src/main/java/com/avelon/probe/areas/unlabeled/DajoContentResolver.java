package com.avelon.probe.areas.unlabeled;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.avelon.probe.MainActivity;
import com.avelon.probe.areas.AbstractManager;

public class DajoContentResolver extends AbstractManager {
    private ContentResolver resolver;
    public static String[] permissions = new String[] {};

    public DajoContentResolver(Context ctx) throws Exception {
        super(ctx, permissions);

        /* Manager */
        resolver = ctx.getContentResolver();

        /* Listeners */
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "" + Settings.Secure.getUriFor("assistant"));
        resolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                final String setting = Settings.Secure.getString(ctx.getContentResolver(), "assistant");
                Log.e(TAG, "settings=" + setting);            }
        });

        //final String setting = Settings.Secure.getStringForUser(this.getContentResolver(), "assistant" Settings.Secure.ASSISTANT, 10);
        final String setting = Settings.Secure.getString(resolver, "assistant" /*Settings.Secure.ASSISTANT*/);
        Log.e(TAG, "settings=" + setting);

        com.android.internal.app.AssistUtils utils = new com.android.internal.app.AssistUtils(ctx);
        ComponentName name = utils.getAssistComponentForUser(10);
        Log.e(TAG, "name: " + name);
    }
}
