package com.avelon.probe.areas.carmanagers;

import android.car.Car;
import android.car.navigation.CarNavigationStatusManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.avelon.probe.areas.AbstractManager;

public class DajoCarNavigationStatusManager extends AbstractManager {
    private CarNavigationStatusManager manager;

    public static String[] permissions = new String[] {};

    public DajoCarNavigationStatusManager(Context ctx) throws Exception {
        super(ctx, permissions);

        this.checkFeature(PackageManager.FEATURE_AUTOMOTIVE);

        Car car = Car.createCar(ctx);
        manager = (CarNavigationStatusManager)car.getCarManager(Car.CAR_NAVIGATION_SERVICE);
    }

    @Override
    public void orchestrate() {
        Bundle bundle = new Bundle();
        manager.sendNavigationStateChange(bundle);
    }
}
