package com.aptiv.got.downloadmgr.Google;

import static org.junit.Assert.assertEquals;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GoogleAlarmManager {
    private Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private static final String TAG = GoogleAlarmManager.class.getCanonicalName();

    @Test
    public void test() {
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "alarm=" + alarm.getNextAlarmClock());
    }
}