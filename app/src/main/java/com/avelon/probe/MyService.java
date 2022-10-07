package com.avelon.probe;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.companion.CompanionDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.IBinder;
//import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.DajoBluetoothAdapter;
import com.avelon.probe.areas.DajoBuild;
import com.avelon.probe.areas.DajoEnvironment;
import com.avelon.probe.areas.DajoKeyStore;
import com.avelon.probe.areas.DajoLocale;
import com.avelon.probe.areas.managers.DajoCarNavigationStatusManager;
import com.avelon.probe.areas.managers.DajoDownloadManager;
import com.avelon.probe.areas.managers.DajoLocationManager;
import com.avelon.probe.areas.managers.DajoUpdateManager;
import com.avelon.probe.areas.managers.DajoWifiManager;
import com.avelon.probe.areas.managers.DajoWindowManager;

import java.util.Iterator;
import java.util.List;

public class MyService extends Service {
    private static final String TAG = MyService.class.getCanonicalName();

    public MyService() {
    }

    //@RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint({"MissingPermission", "Range"})
    @Override
    public void onCreate() {
        super.onCreate();

       // MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("New carplay session...").setTitle("Carplay");
        builder.setPositiveButton("connect", (dialog, id) -> {
        });

        builder.setNegativeButton("cancel", (dialog, id) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
       */

        /*
        //unsigned int deviceScreenScale() const override;
        Log.e(TAG,"deviceScreenScale: " + dm.scaledDensity);

        //std::string deviceModelyear() const override;
        */
/*

*/
        Log.e(TAG, "-- START --");

        try {
            AbstractManager[] managers = {
                    new DajoBluetoothAdapter(this),
                    new DajoBuild(this),
                    new DajoCarNavigationStatusManager(this),
                    new DajoDownloadManager(this),
                    new DajoEnvironment(this),
                    new DajoKeyStore(this),
                    new DajoLocale(this),
                    new DajoLocationManager(this),
                    new DajoUpdateManager(this),
                    new DajoWifiManager(this),
                    new DajoWindowManager(this),
            };

            for (AbstractManager manager : managers) {
                Log.e(TAG, "---- " + manager.getClass().getSimpleName() + " ----");
                //manager.orchestrate();
            }
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }

        accessibilityManager();
        //accountManager();
        //activityManager();
        //alarmManager();
        //audioManager();
        //bluetoothManager();
        //carNavigationStatusManager();
        //carPropertyManager();
        //companionDeviceManager();
        //connectivityManager();
        //devicePolicyManager(componentName);
        //dropboxManager();
        //mediaSession();
        //packageManager();
        //secureSettings();
        //storageManager();
        //systemProperties();
        //systemSettings();
        //telecomManager();
        //telephonyService();

        Log.e(TAG, "-- END --");
    }

    private void devicePolicyManager(ComponentName componentName) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        Log.e(TAG, "Admins:");
        for(ComponentName name : devicePolicyManager.getActiveAdmins()) {
            Log.e(TAG, "name=" + name);
        }
        devicePolicyManager.lockNow();
        //devicePolicyManager.setOrganizationId("Aptiv");
        //devicePolicyManager.setOrganizationName("", "Aptiv");
        //Log.e(TAG, "failed logins=" + devicePolicyManager.getCurrentFailedPasswordAttempts());
        //devicePolicyManager.
        Log.e(TAG, "isAdmin=" + devicePolicyManager.isAdminActive(componentName));
    }

    private void systemSettings() {
        Log.e(TAG, "autotime: " + Settings.System.getString(getContentResolver(), Settings.System.AUTO_TIME));
        Settings.System.putInt(getContentResolver(), Settings.System.AUTO_TIME, 0);
        Log.e(TAG, "autotime: " + Settings.System.getString(getContentResolver(), Settings.System.AUTO_TIME));
    }

    /*
        static String generateSaveFile(Context context, String url, String hint,
                                       String contentDisposition, String contentLocation, String mimeType, int destination)
                throws IOException {

            final File parent;
            final File[] parentTest;
            String name = null;

            if (destination == Downloads.Impl.DESTINATION_FILE_URI) {
                final File file = new File(Uri.parse(hint).getPath());
                parent = file.getParentFile().getAbsoluteFile();
                parentTest = new File[] { parent };
                name = file.getName();
            } else {
                parent = getRunningDestinationDirectory(context, destination);
                parentTest = new File[] {
                        parent,
                        getSuccessDestinationDirectory(context, destination)
                };
                name = chooseFilename(url, hint, contentDisposition, contentLocation);
            }

            // Ensure target directories are ready
            for (File test : parentTest) {
                if (!(test.isDirectory() || test.mkdirs())) {
                    throw new IOException("Failed to create parent for " + test);
                }
            }

            if (DownloadDrmHelper.isDrmConvertNeeded(mimeType)) {
                name = DownloadDrmHelper.modifyDrmFwLockFileExtension(name);
            }

            final String prefix;
            final String suffix;
            final int dotIndex = name.lastIndexOf('.');
            final boolean missingExtension = dotIndex < 0;
            if (destination == Downloads.Impl.DESTINATION_FILE_URI) {
                // Destination is explicitly set - do not change the extension
                if (missingExtension) {
                    prefix = name;
                    suffix = "";
                } else {
                    prefix = name.substring(0, dotIndex);
                    suffix = name.substring(dotIndex);
                }
            } else {
                // Split filename between base and extension
                // Add an extension if filename does not have one
                if (missingExtension) {
                    prefix = name;
                    suffix = chooseExtensionFromMimeType(mimeType, true);
                } else {
                    prefix = name.substring(0, dotIndex);
                    suffix = chooseExtensionFromFilename(mimeType, destination, name, dotIndex);
                }
            }

            synchronized (sUniqueLock) {
                name = generateAvailableFilenameLocked(parentTest, prefix, suffix);

                // Claim this filename inside lock to prevent other threads from
                // clobbering us. We're not paranoid enough to use O_EXCL.
                final File file = new File(parent, name);
                file.createNewFile();
                return file.getAbsolutePath();
            }
        }
    */
    private void accessibilityManager() {
        AccessibilityManager accessibility = (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);
        /*accessibility.getEnabledAccessibilityServiceList(AccessibilityManager.FLAG_CONTENT_TEXT).forEach(service -> {
            Log.e(TAG, "service=" + service);
        });
        accessibility.getInstalledAccessibilityServiceList().forEach(instance -> {
            Log.e(TAG, "instance=" + instance);
        });*/
    }
    private void accountManager() {
        AccountManager account = (AccountManager)getSystemService(Context.ACCOUNT_SERVICE);
        for(Account a : account.getAccounts()) {
            Log.e(TAG, "account=" + a);
        }
        for(AuthenticatorDescription auth : account.getAuthenticatorTypes()) {
            Log.e(TAG, "auth=" + auth);
        }
    }
    private void activityManager() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.AppTask task : activityManager.getAppTasks()) {
            Log.e(TAG, "task=" + task.getTaskInfo());
        }
        activityManager.getRecentTasks(10, 0).forEach(task -> {
            Log.e(TAG, "recent=" + task.baseIntent.getComponent());
        });

        //Log.e(TAG, "top application: " + activityManager.getRunningAppProcesses().get(0).processName);
        //Log.e(TAG, "top tasks: " + activityManager.getAppTasks().get(0).getTaskInfo().baseActivity);
        //Log.e(TAG, "top tasks: " + activityManager.getAppTasks().get(0).getTaskInfo().origActivity);

        //activityManager.getAppTasks().forEach(task -> Log.e(TAG, "task=" + task));
    }
    private void alarmManager() {
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "alarm=" + alarm.getNextAlarmClock());
    }
    @SuppressLint("WrongConstant")
    private void audioManager() {
        AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audio.getActivePlaybackConfigurations().forEach(config -> {
            Log.e(TAG, "playback=" + config);
        });

        audio.getActiveRecordingConfigurations().forEach(config -> {
            Log.e(TAG, "recording=" + config);
        });
        for(AudioDeviceInfo device : audio.getDevices(AudioManager.GET_DEVICES_ALL)) {
            Log.e(TAG, "device=" + device.getAddress());
            Log.e(TAG, "device=" + device.getProductName());
        }
    }
    @SuppressLint("MissingPermission")
    private void bluetoothManager() {
        BluetoothManager bluetooth = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        Log.e(TAG, "adapter=" + bluetooth.getAdapter());
        Log.e(TAG, "adapter=" + bluetooth.getConnectedDevices(BluetoothProfile.GATT));

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.enable();
        //adapter.getSupportedProfiles();
    }

    private void carPropertyManager() {
        Car car = Car.createCar(this);
        CarPropertyManager propertyManager = (CarPropertyManager)car.getCarManager(Car.PROPERTY_SERVICE);
        Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.INFO_MAKE, 0));
        Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.NIGHT_MODE, 0));
        //Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.PERF_VEHICLE_SPEED, 0));
        Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.RANGE_REMAINING, 0));
        Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.RANGE_REMAINING, 0));
        Log.e(TAG, "" + propertyManager.getProperty(VehiclePropertyIds.RANGE_REMAINING, 0));
        propertyManager.getPropertyList().forEach(property -> Log.e(TAG, "property=" + property));
        propertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                Log.e(TAG, "286261505=" + carPropertyValue);
            }

            @Override
            public void onErrorEvent(int i, int i1) {
                Log.e(TAG, "erroe=" + i + i1);
            }
        }, VehiclePropertyIds.INFO_MAKE, CarPropertyManager.SENSOR_RATE_UI);
    }
    private void companionDeviceManager() {
        CompanionDeviceManager companionDeviceManager = (CompanionDeviceManager)getSystemService(Context.COMPANION_DEVICE_SERVICE);
        companionDeviceManager.getAssociations().forEach(phone -> {
            Log.e(TAG, "phone=" + phone);
        });
    }
    private void connectivityManager() {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.e(TAG, "activity=" + connectivity.getActiveNetwork());
        Log.e(TAG, "proxy=" + connectivity.getDefaultProxy());
        for(Network network : connectivity.getAllNetworks()) {
            Log.e(TAG, "network=" + network);
        }
    }

    private void dropboxManager() {
        DropBoxManager dropboxManager = (DropBoxManager)getSystemService(Context.DROPBOX_SERVICE);
        dropboxManager.addText("author", "david");
        // /data/system/dropbox
        Log.e(TAG, "dropbox=" + dropboxManager.getNextEntry("data_app_crash", 1000));
        DropBoxManager.Entry entry =  dropboxManager.getNextEntry("data_app_crash", 1000);
        Log.e(TAG, "text=" + entry.getText(100));
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
    private void packageManager() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo info : packages) {
            Log.e(TAG, "info=" + info);
            Log.e(TAG, "info=" + packageManager.getLaunchIntentForPackage(info.className));
        }
        //packageManager.deletePackage("com.aptiv.got.testfullframework", null, 0);
        //packageManager.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
    }
    private void secureSettings() {
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e(TAG, "deviceId=" + id);
    }
    private void storageManager() {
        StorageManager storage = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
        Log.e(TAG, "primary=" + storage.getPrimaryStorageVolume());
        //for(StorageVolume volume : storage.getRecentStorageVolumes()) {
        //    Log.e(TAG, "volume=" + volume);
        //}
    }
    private void systemProperties() {
        //String text = SystemProperties.get("ro.build.user");
        //Log.e(TAG, "text=" + text);
    }
    private void telecomManager() {
        final TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        @SuppressLint("MissingPermission")
        final Iterator<PhoneAccountHandle> phoneAccounts = telecomManager.getCallCapablePhoneAccounts().listIterator();
        while (phoneAccounts.hasNext()) {
            final PhoneAccountHandle phoneAccountHandle = phoneAccounts.next();
            final PhoneAccount phoneAccount = telecomManager.getPhoneAccount(phoneAccountHandle);
            Log.e(TAG, "phoneAccountHandle=" + phoneAccountHandle);
            Log.e(TAG, "phoneAccount=" + phoneAccount);
        }
    }
    private void telephonyService() {
        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.e(TAG, "mobileCarrier=" + telephony.getNetworkOperatorName());
    }
    @SuppressLint("MissingPermission")
    private void wifiManager() {


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


/*
    AsyncTask task = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String cert = "-----BEGIN CERTIFICATE-----\n" +
                        "MIICEjCCAXsCAg36MA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG\n" +
                        "A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE\n" +
                        "MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl\n" +
                        "YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw\n" +
                        "ODIyMDUyNjU0WhcNMTcwODIxMDUyNjU0WjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE\n" +
                        "CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs\n" +
                        "ZS5jb20wXDANBgkqhkiG9w0BAQEFAANLADBIAkEAm/xmkHmEQrurE/0re/jeFRLl\n" +
                        "8ZPjBop7uLHhnia7lQG/5zDtZIUC3RVpqDSwBuw/NTweGyuP+o8AG98HxqxTBwID\n" +
                        "AQABMA0GCSqGSIb3DQEBBQUAA4GBABS2TLuBeTPmcaTaUW/LCB2NYOy8GMdzR1mx\n" +
                        "8iBIu2H6/E2tiY3RIevV2OW61qY2/XRQg7YPxx3ffeUugX9F4J/iPnnu1zAxxyBy\n" +
                        "2VguKv4SWjRFoRkIfIlHX0qVviMhSlNy2ioFLy7JcPZb+v3ftDGywUqcBiVDoea0\n" +
                        "Hn+GmxZA\n" +
                        "-----END CERTIFICATE-----";

                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);

                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                keyStore.setCertificateEntry("cert", cf.generateCertificate(new ByteArrayInputStream(cert.getBytes())));
                Enumeration<String> aliases = keyStore.aliases();
                while(aliases.hasMoreElements()) {
                    Log.e(TAG, "alias: " +  aliases.nextElement());
                }

                String algorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
                tmf.init(keyStore);
                for(TrustManager trust : tmf.getTrustManagers()) {
                    Log.e(TAG, "trust:" + trust);
                }
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                URL url = new URL("https://jfrogedc.gsep.daimler.com/artifactory/HMIEVO_MAVEN-HMI_2_0_Truck_MAVEN_Release");
                Log.e(TAG, "" + url.getAuthority());
                HttpsURLConnection urlConnection = (HttpsURLConnection )url.openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                //urlConnection.setRequestProperty("KeepAlive", true);
                InputStream in = urlConnection.getInputStream();
                while (in.available() > 0) {
                    byte[] b = new byte[1024];
                    in.read(b);
                    Log.e(TAG, ": " + b);
                }
            }
            catch(Exception e) {
                Log.e(TAG, "e", e);
            }
            return null;
        };
    };
    task.execute();

*/