package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoConnectivityManager extends AbstractManager {
    private ConnectivityManager manager;
    public static String[] permissions = new String[] {};

    public DajoConnectivityManager(Context ctx) throws Exception {
        super(DajoConnectivityManager.class, ctx, permissions);

        manager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "activity=" + manager.getActiveNetwork());
        Log.i(TAG, "proxy=" + manager.getDefaultProxy());
        for(Network network : manager.getAllNetworks()) {
            Log.i(TAG, "network=" + network);
        }
    }
}
