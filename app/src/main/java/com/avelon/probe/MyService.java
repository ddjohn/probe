package com.avelon.probe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.managers.DajoMediaSessionManager;
import com.avelon.probe.areas.unlabeled.DajoContentResolver;
import com.avelon.probe.areas.carmanagers.DajoCarDrivingStateManager;
import com.avelon.probe.areas.carmanagers.DajoCarUserManager;
import com.avelon.probe.areas.carmanagers.DajoCarUxRestrictionManager;
import com.avelon.probe.areas.managers.DajoAlarmManager;
import com.avelon.probe.areas.unlabeled.DajoAlertDialog;
import com.avelon.probe.areas.managers.DajoBluetoothManager;
import com.avelon.probe.areas.carmanagers.DajoCarNavigationStatusManager;
import com.avelon.probe.areas.carmanagers.DajoCarPropertyManager;
import com.avelon.probe.areas.managers.DajoStorageManager;
import com.avelon.probe.areas.managers.DajoTelecomManager;
import com.avelon.probe.areas.lifecycle.MyServiceLifecycle;
import com.avelon.probe.areas.managers.DajoDropboxManager;
import com.avelon.probe.areas.unlabeled.DajoLocale;
import com.avelon.probe.areas.unlabeled.DajoSecureSettings;
import com.avelon.probe.areas.managers.DajoPackageManager;
import com.avelon.probe.areas.managers.DajoAccountManager;
import com.avelon.probe.areas.managers.DajoActivityManager;
import com.avelon.probe.areas.managers.DajoConnectivityManager;
import com.avelon.probe.areas.unlabeled.DajoBuild;
import com.avelon.probe.areas.unlabeled.DajoEnvironment;
import com.avelon.probe.areas.unlabeled.DajoKeyStore;
import com.avelon.probe.areas.unlabeled.DajoSystemProperties;
import com.avelon.probe.areas.unlabeled.DajoSystemSettings;
import com.avelon.probe.areas.managers.DajoDownloadManager;
import com.avelon.probe.areas.managers.DajoLocationManager;
import com.avelon.probe.areas.managers.DajoUpdateManager;
import com.avelon.probe.areas.managers.DajoWifiManager;
import com.avelon.probe.areas.managers.DajoWindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class MyService extends MyServiceLifecycle {
    private static final String TAG = MyService.class.getCanonicalName();

    private static Class classes[] = new Class[] {
            DajoAccountManager.class, 
            DajoActivityManager.class,
            DajoActivityManager.class,
            DajoAlarmManager.class,
            DajoAlertDialog.class,
            DajoBluetoothManager.class,
            DajoBuild.class,
            DajoConnectivityManager.class,
            DajoContentResolver.class,
            DajoDownloadManager.class,
            DajoDropboxManager.class,
            DajoEnvironment.class,
            DajoKeyStore.class,
            DajoLocale.class,
            DajoLocationManager.class,
            DajoMediaSessionManager.class,
            DajoPackageManager.class,
            DajoSecureSettings.class,
            DajoStorageManager.class,
            DajoSystemProperties.class,
            DajoSystemSettings.class,
            DajoTelecomManager.class,
            DajoUpdateManager.class,
            DajoWifiManager.class,
            DajoWindowManager.class,
            
            DajoCarDrivingStateManager.class,
            DajoCarNavigationStatusManager.class,
            DajoCarUserManager.class,
            DajoCarUxRestrictionManager.class,
            DajoCarPropertyManager.class,
    };
    
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for(Class clazz : classes) {
            try {
                Constructor constructor = clazz.getConstructor(Context.class);
                Object object = constructor.newInstance(this);
                Method method = clazz.getMethod("orchestrate");
                method.invoke(object);
            }
            catch (Exception e) {
                Log.e(TAG, "exception", e);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}