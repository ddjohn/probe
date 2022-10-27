package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.drivingstate.CarDrivingStateEvent;
import android.car.drivingstate.CarDrivingStateManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarDrivingStateManager extends AbstractManager implements CarDrivingStateManager.CarDrivingStateEventListener {
    private CarDrivingStateManager manager;
    public static String[] permissions = new String[] {};

    public DajoCarDrivingStateManager(Context ctx) throws Exception {
        super(ctx, permissions);

        this.checkFeature(PackageManager.FEATURE_AUTOMOTIVE);

        /* Manager */
        Car car = Car.createCar(ctx);
        manager = (CarDrivingStateManager)car.getCarManager(Car.CAR_DRIVING_STATE_SERVICE);

        /* Listeners */
        manager.registerListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.registerListener(carDrivingStateEvent -> {
            Log.e(TAG, "_event=" + carDrivingStateEvent.eventValue);
            Log.e(TAG, "_time=" + carDrivingStateEvent.timeStamp);
            Log.e(TAG, "_content" + carDrivingStateEvent.describeContents());
            Log.e(TAG, "_string" + carDrivingStateEvent.toString());
        });
        Log.e(TAG, "driving state=" + manager.getCurrentCarDrivingState());
    }

    @Override
    public void onDrivingStateChanged(CarDrivingStateEvent carDrivingStateEvent) {
        Log.e(TAG, "cb:onDrivingStateChanged(): " + carDrivingStateEvent);
    }
}
