package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoBluetoothManager extends AbstractManager {
    private BluetoothAdapter adapter;
    private BluetoothManager manager;
    public static String[] permissions = new String[] {};

    public DajoBluetoothManager(Context ctx) throws Exception {
        super(DajoBluetoothManager.class, ctx, permissions);

        checkFeature(PackageManager.FEATURE_BLUETOOTH);

        manager = (BluetoothManager)ctx.getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "adapter=" + manager.getAdapter());
        Log.i(TAG, "adapter=" + adapter);
        Log.i(TAG, "adapter=" + manager.getConnectedDevices(BluetoothProfile.GATT));

        adapter.enable();
        //adapter.getSupportedProfiles();

        //adapter.getSupportedProfiles();
        //Log.e(TAG, "" + android.bluetooth.BluetoothHeadsetClientCall.CALL_STATE_ACTIVE);
        //Log.e("MUNGO", "" + android.bluetooth.BluetoothHeadsetClientCall.CALL_STATE_ACTIVE);
        //Log.e("MUNGO", "" + BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
    }
}
