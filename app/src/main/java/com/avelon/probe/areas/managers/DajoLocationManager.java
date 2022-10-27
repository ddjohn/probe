package com.avelon.probe.areas.managers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;

import java.util.concurrent.Executor;

public class DajoLocationManager extends AbstractManager implements OnNmeaMessageListener {
    private LocationManager manager;
    public static String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

    @SuppressLint("MissingPermission")
    public DajoLocationManager(Context ctx) throws Exception {
        super(ctx, permissions);

        this.checkFeature(PackageManager.FEATURE_LOCATION_GPS);

        /* Manager */
        manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        /* Listeners */
        manager.addNmeaListener(ctx.getMainExecutor(), this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() {
        Log.i("TAG", "location=" + manager.getLastKnownLocation(manager.getGnssHardwareModelName()));
    }

    @Override
    public void onNmeaMessage(String s, long l) {
        Log.e(TAG, "cm:onNmeaMessage(): " + s);
    }
}
