package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarPropertyManager extends AbstractManager {
    private CarPropertyManager manager;
    public static String[] permissions = new String[] {};

    public DajoCarPropertyManager(Context ctx) throws Exception {
        super(ctx, permissions);

        Car car = Car.createCar(ctx);
        manager = (CarPropertyManager)car.getCarManager(Car.PROPERTY_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Log.i(TAG, "" + manager.getProperty(VehiclePropertyIds.INFO_MAKE, 0));
        Log.i(TAG, "" + manager.getProperty(VehiclePropertyIds.NIGHT_MODE, 0));
        //Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED, 0));
        Log.i(TAG, "" + manager.getProperty(VehiclePropertyIds.RANGE_REMAINING, 0));
        manager.getPropertyList().forEach(property -> Log.i(TAG, "property=" + property));
        manager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.e(TAG, "286261505=" + carPropertyValue);
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.e(TAG, "error=" + i + i1);
            }
        }, VehiclePropertyIds.INFO_MAKE, CarPropertyManager.SENSOR_RATE_UI);
    }
}
