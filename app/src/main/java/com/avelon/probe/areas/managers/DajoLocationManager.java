package com.avelon.probe.areas.managers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.car.Car;
import android.content.Context;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;

public class DajoLocationManager extends AbstractManager {
    private LocationManager manager;
    public static String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};

    public DajoLocationManager(Context ctx) throws Exception {
        super(ctx, permissions);

        Car car = Car.createCar(ctx);
        manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() {

        manager.addNmeaListener(new OnNmeaMessageListener() {
            @Override
            public void onNmeaMessage(String s, long l) {
                Log.e("TAG", "message=" + s);
            }
        });
        Log.e("TAG", "location=" + manager.getLastKnownLocation(manager.getGnssHardwareModelName()));
    }
}
