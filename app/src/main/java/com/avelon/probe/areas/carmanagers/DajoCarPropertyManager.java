package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.VehicleAreaType;
import android.car.VehiclePropertyAccess;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.Random;
import java.util.Timer;

public class DajoCarPropertyManager extends AbstractManager implements CarPropertyManager.CarPropertyEventCallback {
    private CarPropertyManager manager;
    public static String[] permissions = new String[] {};
    boolean b = false;

    public DajoCarPropertyManager(Context ctx) throws Exception {
        super(ctx, permissions);

        // Get manager
        Car car = Car.createCar(ctx);
        manager = (CarPropertyManager)car.getCarManager(Car.PROPERTY_SERVICE);

        // Register listeners

        manager.getPropertyList().forEach(property -> {
            Log.i(TAG, "Register " + property);
            manager.registerCallback(this, property.getPropertyId(), CarPropertyManager.SENSOR_RATE_ONCHANGE);
            manager.registerCallback(this, property.getPropertyId(), CarPropertyManager.SENSOR_RATE_NORMAL);
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.e(TAG, "" + manager.getProperty(VehiclePropertyIds.INFO_MAKE, 0));
        Log.e(TAG, "" + manager.getProperty(VehiclePropertyIds.NIGHT_MODE, 0));
        Log.e(TAG, "" + manager.getProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED, 0));
        Log.e(TAG, "" + manager.getProperty(VehiclePropertyIds.RANGE_REMAINING, 0));

        manager.getPropertyList().forEach(property -> Log.e(TAG, "property=" + property));

        Thread t = new Thread(() -> {
            while(true) {
                b = (b == false ? true : false);
                Log.e(TAG, "run(): " + b);
                manager.setProperty(Integer.class, VehiclePropertyIds.HVAC_TEMPERATURE_DISPLAY_UNITS, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, b ? 0 : 1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        manager.setProperty(Integer.class, VehiclePropertyIds.HVAC_TEMPERATURE_DISPLAY_UNITS, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, 1);
        manager.setProperty(Integer.class, VehiclePropertyIds.HVAC_TEMPERATURE_DISPLAY_UNITS, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, 0);
    }

    @Override
    public void onChangeEvent(CarPropertyValue carPropertyValue) {
        Log.e(TAG, "onChangeEvent(): " + carPropertyValue);
    }

    @Override
    public void onErrorEvent(int i, int i1) {
        Log.e(TAG, "onErrorEvent(): " + i + i1);
    }
}
