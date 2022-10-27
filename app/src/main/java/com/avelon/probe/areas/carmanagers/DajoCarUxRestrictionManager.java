package com.avelon.probe.areas.carmanagers;

import android.annotation.SuppressLint;
import android.car.Car;
import android.car.drivingstate.CarUxRestrictions;
import android.car.drivingstate.CarUxRestrictionsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoCarUxRestrictionManager extends AbstractManager implements CarUxRestrictionsManager.OnUxRestrictionsChangedListener {
    private CarUxRestrictionsManager manager;
    public static String[] permissions = new String[] {};

    public DajoCarUxRestrictionManager(Context ctx) throws Exception {
        super(ctx, permissions);

        this.checkFeature(PackageManager.FEATURE_AUTOMOTIVE);

        /* Manager */
        Car car = Car.createCar(ctx);
        manager = (CarUxRestrictionsManager)car.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE);

        /* LIsteners*/
        manager.registerListener(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        manager.registerListener(new CarUxRestrictionsManager.OnUxRestrictionsChangedListener() {
            @Override
            public void onUxRestrictionsChanged(CarUxRestrictions carUxRestrictions) {
                Log.e(TAG, "restrictions: " + carUxRestrictions.getActiveRestrictions());
                Log.e(TAG, "contents: " + carUxRestrictions.describeContents());
                Log.e(TAG, "time: " + carUxRestrictions.getTimeStamp());
                Log.e(TAG, "depth: " + carUxRestrictions.getMaxContentDepth());
                Log.e(TAG, "legth: " + carUxRestrictions.getMaxRestrictedStringLength());
                Log.e(TAG, "items: " + carUxRestrictions.getMaxCumulativeContentItems());
                Log.e(TAG, "distoptimize: " + carUxRestrictions.isRequiresDistractionOptimization());
                Log.e(TAG, "ux: " + carUxRestrictions.toString());

                int active = carUxRestrictions.getActiveRestrictions();
                Log.e(TAG, "active=" + toBinaryString(active, 4));
                Log.e(TAG, "pad=" + CarUxRestrictions.UX_RESTRICTIONS_NO_DIALPAD);
                Log.e(TAG, "filt=" + CarUxRestrictions.UX_RESTRICTIONS_NO_FILTERING);
                Log.e(TAG, "string=" + CarUxRestrictions.UX_RESTRICTIONS_LIMIT_STRING_LENGTH);
                Log.e(TAG, "key=" + CarUxRestrictions.UX_RESTRICTIONS_NO_KEYBOARD);
                Log.e(TAG, "video=" + CarUxRestrictions.UX_RESTRICTIONS_NO_VIDEO);
                Log.e(TAG, "content=" + CarUxRestrictions.UX_RESTRICTIONS_LIMIT_CONTENT);
                Log.e(TAG, "setup=" + CarUxRestrictions.UX_RESTRICTIONS_NO_SETUP);
                Log.e(TAG, "test=" + CarUxRestrictions.UX_RESTRICTIONS_NO_TEXT_MESSAGE);
                Log.e(TAG, "voice=" + CarUxRestrictions.UX_RESTRICTIONS_NO_VOICE_TRANSCRIPTION);
            }
        });

        Log.e(TAG, "current=" + manager.getCurrentCarUxRestrictions());
    }

    public String toBinaryString(int number, int groupSize) {
        String binary = Integer.toBinaryString(number);

        StringBuilder result = new StringBuilder(binary);
        for (int i = 1; i < binary.length(); i++) {
            if (i % groupSize == 0) {
                result.insert(binary.length() - i, " ");
            }
        }
        return result.toString();
    }

    @Override
    public void onUxRestrictionsChanged(CarUxRestrictions carUxRestrictions) {
        Log.e(TAG, "cb:onUxRestrictionsChanged(): " + carUxRestrictions);
    }
}
