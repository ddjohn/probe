package com.avelon.probe;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.drivingstate.CarDrivingStateManager;
import android.car.drivingstate.CarUxRestrictions;
import android.car.drivingstate.CarUxRestrictionsManager;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.car.navigation.CarNavigationStatusManager;
import android.companion.CompanionDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.LocaleData;
import android.icu.util.ULocale;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.LocaleList;
//import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

import com.avelon.probe.concepts.EncodeAndMux;
import com.avelon.probe.concepts.FlatBuffers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyService2 extends Service {
    private static final String TAG = MyService2.class.getCanonicalName();

    public void test() {
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

        String url = "https://www.sygic.com/assets/enterprise/img/Sygic_logo.svg";
        //String url = "http://localhost:8080/hello.txt";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Title");
        request.setDescription("Description");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DCIM, "Sygic_logo.svg");
        manager.enqueue(request);

        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = manager.query(query);
        Log.e(TAG, "count=" + cursor.getCount());
        int columns[] = {
                cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR),
                cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION),
                cursor.getColumnIndex(DownloadManager.COLUMN_ID),
                cursor.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP),
                cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI),
                cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE),
                cursor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI),
                cursor.getColumnIndex(DownloadManager.COLUMN_REASON),
                cursor.getColumnIndex(DownloadManager.COLUMN_STATUS),
                cursor.getColumnIndex(DownloadManager.COLUMN_TITLE),
                cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES),
                cursor.getColumnIndex(DownloadManager.COLUMN_URI),

        };
        String s2 = "";
        for(int c : columns) {
            s2 += "," + cursor.getColumnName(c);
        }
        Log.e(TAG, "name=" + s2);

        while (cursor.moveToNext()) {
            String s = "";
            for(int c : columns) {
                s += "," + cursor.getString(c);
            }
            Log.e(TAG, "name=" + s);
        }
        cursor.close();

/*
        File f = new File("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/");
        for(File file : f.listFiles()) {
            Log.e(TAG, "file=" + file);
            if(file.isDirectory()) {
                for (File file2 : f.listFiles()) {
                    Log.e(TAG, "file=" + file2);
                }
            }
        };
*/
/*

        Log.e(TAG, "----------------");

        File f = new File("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies");
        Log.e(TAG, "dir=" + f.isDirectory());
        Log.e(TAG, "dir=" + f.mkdirs());
        Log.e(TAG, "" + !(f.isDirectory() || f.mkdirs()));
        Log.e(TAG, "----------------");

        final File file = new File(Uri.parse("/storage/emulated/10/Android/data/com.aptiv.got.downloadmgr/files/Movies").getPath());
        Log.e(TAG, "file=" + file);
        File parent = file.getParentFile().getAbsoluteFile();
        Log.e(TAG, "parent=" + parent);
        File[] parentTest = new File[] { parent };
        String name = file.getName();
        Log.e(TAG, "file=" + name);

        // Ensure target directories are ready
        for (File test : parentTest) {
            Log.e(TAG, "Scan " + test);
            Log.e(TAG, "Scan " + test.isDirectory());
            Log.e(TAG, "Scan " + test.mkdirs());
            if (!(test.isDirectory() || test.mkdirs())) {
                Log.e(TAG, "Failed to create parent for " + test);
                //throw new IOException("Failed to create parent for " + test);
            }
        }
*/
/*
        try {
            File testDir = new File(this.getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + "maps");
            Log.e(TAG, "path=" + testDir);
            Log.e(TAG, "Create directory: " + testDir.mkdirs());
            File testFile = new File(testDir, "testmap.txt");
            Log.e(TAG, "path=" + testFile);
            FileOutputStream stream = new FileOutputStream(testFile);
            stream.write("hello world".getBytes());
            stream.close();
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }
*/
        Log.e(TAG, "----------------");

    }

    //@RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint({"MissingPermission", "Range"})
    @Override
    public void onCreate() {
        super.onCreate();

        //test();

        try {
            new FlatBuffers(this);
            //new MQTT(this);
            //new RecursiveScan(this, "/data/media/10/Android/data/com.aptiv.got.downloadmgr/files/");
            //new WebServer(this);
            EncodeAndMux mux = new EncodeAndMux(this.getFilesDir());
            mux.testEncodeVideoToMp4(); 
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }

        //accessibilityManager();
        //accountManager();
        //activityManager();
        //alarmManager();
        //alertDialog();
        //audioManager();
        //bluetoothManager();
        //build();
        //carDrivingStateManager();
        //carNavigationStatusManager();
        //carPropertyManager();
        //carUxRestrictionManager();
        //companionDeviceManager();
        //connectivityManager();
        //devicePolicyManager(componentName);
        //downloadManager();
        //dropboxManager();
        //environment();
        //keyStore();
        //locale();
        //locationManager();
        //mediaProjectionManager();
        //mediaSession();
        //packageManager();
        //secureSettings();
        //storageManager();
        //systemProperties();
        //systemSettings();
        //telecomManager();
        //telephonyService();
        //wifiManager();
        //updateManager();
        //windowManager();
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
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("New carplay session...").setTitle("Carplay");
        builder.setPositiveButton("connect", (dialog, id) -> {
        });

        builder.setNegativeButton("cancel", (dialog, id) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
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

        Log.e(TAG, "" + BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);

        //Log.e(TAG, "" + BluetoothHeadsetClientCall.CALL_STATE_ACTIVE);
        //List<?> profiles = adapter.getSupportedProfiles();

        //adapter.getSupportedProfiles();
    }
    private void build() {
        Log.e(TAG, "deviceMake=" + Build.MANUFACTURER);
        Log.e(TAG, "deviceName=" + Build.DEVICE);
        Log.e(TAG, "deviceName=" + Build.MODEL);
        Log.e(TAG, "version=Android " + Build.VERSION.RELEASE);
    }
    private void carDrivingStateManager() {
        Car car = Car.createCar(this);
        CarDrivingStateManager carDrivingStateManager = (CarDrivingStateManager)car.getCarManager(Car.CAR_DRIVING_STATE_SERVICE);
        carDrivingStateManager.registerListener(carDrivingStateEvent -> {
            Log.e(TAG, "_event=" + carDrivingStateEvent.eventValue);
            Log.e(TAG, "_time=" + carDrivingStateEvent.timeStamp);
            Log.e(TAG, "_content" + carDrivingStateEvent.describeContents());
            Log.e(TAG, "_string" + carDrivingStateEvent.toString());
        });
        Log.e(TAG, "driving state=" + carDrivingStateManager.getCurrentCarDrivingState());
    }
    private void carNavigationStatusManager() {
        Car car = Car.createCar(this);
        CarNavigationStatusManager carNavigationStatusManager = (CarNavigationStatusManager)car.getCarManager(Car.CAR_NAVIGATION_SERVICE);

        Bundle bundle = new Bundle();
        carNavigationStatusManager.sendNavigationStateChange(bundle);
        // NavigationStateProto proto;
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
    private void carUxRestrictionManager() {
        Car car = Car.createCar(this);
        CarUxRestrictionsManager carUxRestrictionsManager = (CarUxRestrictionsManager)car.getCarManager(Car.CAR_UX_RESTRICTION_SERVICE);
        carUxRestrictionsManager.registerListener(new CarUxRestrictionsManager.OnUxRestrictionsChangedListener() {
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

        Log.e(TAG, "current=" + carUxRestrictionsManager.getCurrentCarUxRestrictions());
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
    private void downloadManager() {
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //request.setAllowedOverRoaming(false);
        //request.allowScanningByMediaScanner();
        //request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "habba.jpg");
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_ALARMS, "habba.jpg");
        //request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Downloads" + File.separator + "a.jpg")));

        //Log.e(TAG, "request=" + request.toString());

        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

        String url = "https://www.sygic.com/assets/enterprise/img/Sygic_logo.svg";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Title");
        request.setDescription("Description");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_MOVIES, "Sygic_logo.svg");
        manager.enqueue(request);

        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = manager.query(query);
        Log.e(TAG, "count=" + cursor.getCount());
        while (cursor.moveToNext()) {
            //Log.e(TAG, "name=" + cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION)) + cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));
        }
        cursor.close();

//        String filename = URLUtil.guessFileName(url,null, MimeTypeMap.getFileExtensionFromUrl(url));

        //Uri uri = Uri.parse("file:///data/media/0/Android/data/com.aptiv.got.downloadmgr/files/" + filename);
        //Log.e(TAG, "getFilesDir=" + getFilesDir());
        //Log.e(TAG, "getExternalFilesDir=" + getExternalFilesDir("test"));
        //Log.e(TAG, "getExternalFilesDirs=" + getExternalFilesDirs("test")[0]);
/*
        for(File f : this.getExternalFilesDirs(null)) {
            Log.e(TAG, "path=" + f);
        }

        // Emulated storage

        // Unsupported part
        //Uri uri = Uri.parse("file:///data/oem_data/" + File.separator + filename);
        //request.setDestinationUri(uri);
        File f = new File("/data/oem_data");
        Log.e(TAG, "aaa=" + f.exists());
        File file = new File("/data/oem_data/", filename);
        request.setDestinationUri(Uri.fromFile(file));
*/
    }
    private void dropboxManager() {
        DropBoxManager dropboxManager = (DropBoxManager)getSystemService(Context.DROPBOX_SERVICE);
        dropboxManager.addText("author", "david");
        // /data/system/dropbox
        Log.e(TAG, "dropbox=" + dropboxManager.getNextEntry("data_app_crash", 1000));
        DropBoxManager.Entry entry =  dropboxManager.getNextEntry("data_app_crash", 1000);
        Log.e(TAG, "text=" + entry.getText(100));
    }
    private void environment() {
        Log.e(TAG, "dir=" + Environment.getExternalStorageDirectory());
        Log.e(TAG, "dir=" + Environment.getExternalStorageState());
        Log.e(TAG, "dir=" + Environment.DIRECTORY_DOCUMENTS);
        //Log.e(TAG, "dir=" + Environment.getStorageDirectory());
        Log.e(TAG, "dir=" + Environment.getRootDirectory());
        Log.e(TAG, "dir=" + Environment.getDownloadCacheDirectory());
        Log.e(TAG, "dir=" + Environment.getDataDirectory());
    }
    private void keyStore() {
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

        String rootCert = cert;
        String clientCert = cert;
        String certCert = cert;

        try {
            {
                // Diagnostic Routine
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);

                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                keyStore.setCertificateEntry("root_alias", cf.generateCertificate(new ByteArrayInputStream(rootCert.getBytes())));
                keyStore.setCertificateEntry("client_alias", cf.generateCertificate(new ByteArrayInputStream(clientCert.getBytes())));
                keyStore.setCertificateEntry("cert_alias", cf.generateCertificate(new ByteArrayInputStream(certCert.getBytes())));
            }

            // Android Auto
            {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);

                Enumeration<String> aliases = keyStore.aliases();
                while(aliases.hasMoreElements()) {
                    Log.e(TAG, "alias=" + aliases.nextElement());
                }

                Log.e(TAG, "root=" + keyStore.getCertificate("root_alias"));
                Log.e(TAG, "root=" + keyStore.getCertificate("cert_alias"));
                Log.e(TAG, "root=" + keyStore.getCertificate("client_alias"));
            }
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private void locale() {
        Log.e(TAG, "locale=" + Locale.getDefault());
        Log.e(TAG, "locale=" + ULocale.forLocale(Locale.getDefault()));
        Log.e(TAG, "locale=" + LocaleData.getMeasurementSystem(ULocale.forLocale(Locale.getDefault())));
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.SI);
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.UK);
        Log.e(TAG, "Metric=" + LocaleData.MeasurementSystem.US);
        Log.e(TAG, "Timezone=" + TimeZone.getDefault());
        Log.e(TAG, "Language=" + Locale.getDefault().getLanguage());
        Log.e(TAG, "language=" + Locale.getDefault().getDisplayLanguage());
        Log.e(TAG, "language=" + Locale.getDefault().getISO3Language());
        Log.e(TAG, "language=" + Locale.getDefault().getDisplayCountry());
        Log.e(TAG, "language=" + Locale.getDefault().getCountry());

        LocaleList l = LocaleList.getDefault();
        for(int i = 0; i < l.size(); i++) {
            Log.e(TAG, "locale=" + l.get(i));
        }
        Locale[] locales = Locale.getAvailableLocales();
        for(Locale locale : locales) {
            Log.e(TAG, "locale=" + locale);
        }
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
    }
    @SuppressLint("MissingPermission")
    private void locationManager() {
        LocationManager location = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Log.e("TAG", "model=" + location.getGnssHardwareModelName());
        location.addNmeaListener(new OnNmeaMessageListener() {
            @Override
            public void onNmeaMessage(String s, long l) {
                Log.e("TAG", "message=" + s);
            }
        });
        Log.e("TAG", "location=" + location.getLastKnownLocation(location.getGnssHardwareModelName()));
    }
    private void mediaProjectionManager() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
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
    private void systemSettings() {
        Log.e(TAG, "autotime: " + Settings.System.getString(getContentResolver(), Settings.System.AUTO_TIME));
        Settings.System.putInt(getContentResolver(), Settings.System.AUTO_TIME, 0);
        Log.e(TAG, "autotime: " + Settings.System.getString(getContentResolver(), Settings.System.AUTO_TIME));
    }
    private void telecomManager() {
        final TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        // PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(this.getApplicationContext(), MyService.class), "example");
        //PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "example").setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build();
        //PhoneAccountHandle phoneAccountHandle = telecomManager.getDefaultOutgoingPhoneAccount("ytetet");
        //Bundle extras = new Bundle();
        //extras.putParcelable(TelecomManager, uri);
        //telecom.addNewIncomingCall(phoneAccountHandle, extras);

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
    private void updateManager() {
        /*
        UpdateEngine updateEngine = new UpdateEngine();
        Log.e(TAG, "updateEngine=" + updateEngine);

        updateEngine.bind(new UpdateEngineCallback() {

            @Override
            public void onStatusUpdate(int i, float v) {
            }

            @Override
            public void onPayloadApplicationComplete(int i) {
            }
        });
        updateEngine.applyPayload("file:///data/ota_package/payload.bin",0, 22112, getInfo());
    */
    }
    @SuppressLint("MissingPermission")
    private void wifiManager() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.getScanResults().forEach(scan -> {
            Log.e(TAG, "scan=" + scan);
        });

        wifi.getConfiguredNetworks().forEach(network -> {
            Log.e(TAG, "network=" + network);
        });
        Log.e(TAG, "info=" + wifi.getConnectionInfo());
        Log.e(TAG, "dhcp=" + wifi.getDhcpInfo());

    }
    private void windowManager() {
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        double x = dm.widthPixels/dm.xdpi;
        double y = dm.heightPixels/dm.ydpi;
        Log.e(TAG,"deviceScreenSize: " + x + " x " + y);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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