package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.user.CarUserManager;
import android.content.Context;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarUserManager extends AbstractManager {
    private CarUserManager manager;
    public static String[] permissions = new String[] {};

    public DajoCarUserManager(Context ctx) throws Exception {
        super(ctx, permissions);

        Car car = Car.createCar(ctx);
        manager = (CarUserManager) car.getCarManager(Car.CAR_USER_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        //mgr.createGuest("pelle");
        manager.createUser("flagprofile", "android.os.usertype.full.GUEST", 0x00001000);
    }
}
