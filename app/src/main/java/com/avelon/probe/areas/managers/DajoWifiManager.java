package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoWifiManager extends AbstractManager {
    private WifiManager manager;
    public static String[] permissions = new String[] {};

    public DajoWifiManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.getScanResults().forEach(scan -> {
            Log.e(TAG, "scan=" + scan);
        });

        manager.getConfiguredNetworks().forEach(network -> {
            Log.e(TAG, "network=" + network);
        });
        Log.e(TAG, "info=" + manager.getConnectionInfo());
        Log.e(TAG, "dhcp=" + manager.getDhcpInfo());

        Log.e(TAG, "isWifiEnabled=" + manager.isWifiEnabled());

        Log.e(TAG, "is5GHzBandSupported=" + manager.is5GHzBandSupported());
        Log.e(TAG, "is6GHzBandSupported=" + manager.is6GHzBandSupported());
        //Log.e(TAG, "is60GHzBandSupported=" + manager.is60GHzBandSupported());
        //Log.e(TAG, "is24GHzBandSupported=" + manager.is24GHzBandSupported());
    }
}
