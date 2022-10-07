package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

public class DajoBluetoothAdapter extends AbstractManager {
    private BluetoothAdapter adapter;
    public static String[] permissions = new String[] {};

    public DajoBluetoothAdapter(Context ctx) throws Exception {
        super(ctx, permissions);

        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        //adapter.enable();
        //adapter.getSupportedProfiles();
        //Log.e(TAG, "" + android.bluetooth.BluetoothHeadsetClientCall.CALL_STATE_ACTIVE);
        //Log.e("MUNGO", "" + android.bluetooth.BluetoothHeadsetClientCall.CALL_STATE_ACTIVE);
        //List<?> profiles = adapter.getSupportedProfiles();
    }
}
