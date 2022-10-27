package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.drivingstate.CarDrivingStateManager;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarDrivingStateManager extends AbstractManager {
    private CarDrivingStateManager manager;
    public static String[] permissions = new String[] {};

    public DajoCarDrivingStateManager(Context ctx) throws Exception {
        super(ctx, permissions);

        Car car = Car.createCar(ctx);
        manager = (CarDrivingStateManager)car.getCarManager(Car.CAR_DRIVING_STATE_SERVICE);
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
}
