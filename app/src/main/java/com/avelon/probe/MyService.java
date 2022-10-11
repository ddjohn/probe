package com.avelon.probe;

import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.carmanagers.DajoCarDrivingStateManager;
import com.avelon.probe.areas.carmanagers.DajoCarUxRestrictionManager;
import com.avelon.probe.areas.managers.DajoAlarmManager;
import com.avelon.probe.areas.DajoAlertDialog;
import com.avelon.probe.areas.managers.DajoBluetoothManager;
import com.avelon.probe.areas.carmanagers.DajoCarNavigationStatusManager;
import com.avelon.probe.areas.carmanagers.DajoCarPropertyManager;
import com.avelon.probe.areas.managers.DajoStorageManager;
import com.avelon.probe.areas.managers.DajoTelecomManager;
import com.avelon.probe.areas.lifecycle.MyServiceLifecycle;
import com.avelon.probe.areas.managers.DajoDropboxManager;
import com.avelon.probe.areas.DajoSecureSettings;
import com.avelon.probe.areas.managers.DajoPackageManager;
import com.avelon.probe.areas.managers.DajoAccountManager;
import com.avelon.probe.areas.managers.DajoActivityManager;
import com.avelon.probe.areas.managers.DajoConnectivityManager;
import com.avelon.probe.areas.DajoBuild;
import com.avelon.probe.areas.DajoEnvironment;
import com.avelon.probe.areas.DajoKeyStore;
import com.avelon.probe.areas.DajoSystemSettings;
import com.avelon.probe.areas.managers.DajoDownloadManager;
import com.avelon.probe.areas.managers.DajoLocationManager;
import com.avelon.probe.areas.managers.DajoUpdateManager;
import com.avelon.probe.areas.managers.DajoWifiManager;
import com.avelon.probe.areas.managers.DajoWindowManager;
import java.util.List;

public class MyService extends MyServiceLifecycle {
    private static final String TAG = MyService.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        try {
            AbstractManager[] managers = {
                    new DajoAccountManager(this),
                    new DajoActivityManager(this),
                    new DajoActivityManager(this),
                    new DajoAlarmManager(this),
                    new DajoAlertDialog(this),
                    new DajoBluetoothManager(this),
                    new DajoBuild(this),
                    new DajoCarDrivingStateManager(this),
                    new DajoCarNavigationStatusManager(this),
                    new DajoCarUxRestrictionManager(this),
                    new DajoCarPropertyManager(this),
                    new DajoConnectivityManager(this),
                    new DajoDownloadManager(this),
                    new DajoDropboxManager(this),
                    new DajoEnvironment(this),
                    new DajoKeyStore(this),
                    //new DajoLocale(this),
                    new DajoLocationManager(this),
                    new DajoPackageManager(this),
                    new DajoSecureSettings(this),
                    new DajoStorageManager(this),
                    //new DajoSystemProperties(this),
                    new DajoSystemSettings(this),
                    new DajoTelecomManager(this),
                    new DajoUpdateManager(this),
                    new DajoWifiManager(this),
                    new DajoWindowManager(this),
            };
            for (AbstractManager manager : managers) {
                Log.e(TAG, "=== " + manager.getClass().getSimpleName() + " ===");
                manager.orchestrate();
            }
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void mediaSession() {
        MediaSessionManager sessionManager = (MediaSessionManager)getSystemService(Context.MEDIA_SESSION_SERVICE);
        List<MediaController> list = sessionManager.getActiveSessions(null);
        for(MediaController l : list) {
            Log.e(TAG, "" + l);
            Bundle b = l.getExtras();
            String devicename = (String)b.get("DEVIC_NAME");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}