package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.concurrent.Executors;

public class DajoWifiManager extends AbstractManager {
    private WifiManager manager;
    public static String[] permissions = new String[] {};

    public DajoWifiManager(Context ctx) throws Exception {
        super(DajoWifiManager.class, ctx, permissions);

        manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.getScanResults().forEach(scan -> {
            Log.i(TAG, "scan=" + scan);
        });

        manager.getConfiguredNetworks().forEach(network -> {
            Log.i(TAG, "network=" + network);
        });
        Log.i(TAG, "info=" + manager.getConnectionInfo());
        Log.i(TAG, "dhcp=" + manager.getDhcpInfo());

        Log.i(TAG, "isWifiEnabled=" + manager.isWifiEnabled());

        Log.i(TAG, "is5GHzBandSupported=" + manager.is5GHzBandSupported());
        Log.i(TAG, "is6GHzBandSupported=" + manager.is6GHzBandSupported());
        //Log.e(TAG, "is60GHzBandSupported=" + manager.is60GHzBandSupported());
        //Log.e(TAG, "is24GHzBandSupported=" + manager.is24GHzBandSupported());

        manager.registerScanResultsCallback(Executors.newSingleThreadExecutor(), new WifiManager.ScanResultsCallback() {
            @Override
            public void onScanResultsAvailable() {
                Log.e(TAG, "onScanResultAvailable()");
                for (ScanResult result : manager.getScanResults()) {
                    Log.e(TAG, "result=" + result.SSID + result.capabilities);
                }
                Log.e(TAG, "info=" + manager.getConnectionInfo());
                for (WifiConfiguration network: manager.getConfiguredNetworks()) {
                    Log.e(TAG, "network=" + network);
                }
                WifiConfiguration config = new WifiConfiguration();
                config.SSID = "\"felicia\"";
                config.preSharedKey = "\"budapest\"";
                int id = manager.addNetwork(config);

                Log.e(TAG, "id=" + id);
                manager.disconnect();
                manager.enableNetwork(id, true);
                manager.reconnect();
            }
        });
    }
}
