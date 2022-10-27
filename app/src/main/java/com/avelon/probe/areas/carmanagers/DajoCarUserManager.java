package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.user.CarUserManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarUserManager extends AbstractManager implements CarUserManager.UserLifecycleListener {
    private CarUserManager manager;
    public static String[] permissions = new String[] {};

    @SuppressLint("MissingPermission")
    public DajoCarUserManager(Context ctx) throws Exception {
        super(ctx, permissions);

        this.checkFeature(PackageManager.FEATURE_AUTOMOTIVE);

        /* Manager */
        Car car = Car.createCar(ctx);
        manager = (CarUserManager) car.getCarManager(Car.CAR_USER_SERVICE);

        /* Listeners */
        manager.addListener(ctx.getMainExecutor(), this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        //mgr.createGuest("pelle");
        manager.createUser("flagprofile", "android.os.usertype.full.GUEST", 0x00001000);
    }

    @Override
    public void onEvent(CarUserManager.UserLifecycleEvent userLifecycleEvent) {
        Log.e(TAG, "cb:onEvent(): " + userLifecycleEvent);
    }
}
