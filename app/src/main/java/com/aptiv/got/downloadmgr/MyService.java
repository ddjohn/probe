package com.aptiv.got.downloadmgr;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.icu.util.LocaleData;
import android.icu.util.ULocale;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.SystemProperties;
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
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyService extends Service {
    private static final String TAG = MyService.class.getCanonicalName();

    public MyService() {
    }

    //@RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();


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


        //accessibilityManager();
        //accountManager();
        //activityManager();
        //alarmManager();
        //audioManager();
        //bluetoothManager();
        //build();
        //carPropertyManager();
        //connectivityManager();
        //downloadManager();
        //dropboxManager();
        environment();
        //locale();
        //locationManager();
        //mediaSession();
        //packageManager();
        //secureSettings();
        //storageManager();
        //systemProperties();
        //telecomManager();
        //telephonyService();
        //wifiManager();
        //windowManager();


    }


    private void accessibilityManager() {
        AccessibilityManager accessibility = (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibility.getEnabledAccessibilityServiceList(AccessibilityManager.FLAG_CONTENT_TEXT).forEach(service -> {
            Log.e(TAG, "service=" + service);
        });
        accessibility.getInstalledAccessibilityServiceList().forEach(instance -> {
            Log.e(TAG, "instance=" + instance);
        });
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
        //for(ActivityManager.AppTask task : activityManager.getAppTasks()) {
        //    Log.e(TAG, "task=" + task.getTaskInfo());
        //}
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
    }
    private void build() {
        Log.e(TAG, "deviceMake=" + Build.MANUFACTURER);
        Log.e(TAG, "deviceName=" + Build.DEVICE);
        Log.e(TAG, "deviceName=" + Build.MODEL);
        Log.e(TAG, "version=Android " + Build.VERSION.RELEASE);
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
    private void connectivityManager() {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.e(TAG, "activity=" + connectivity.getActiveNetwork());
        Log.e(TAG, "proxy=" + connectivity.getDefaultProxy());
        for(Network network : connectivity.getAllNetworks()) {
            Log.e(TAG, "network=" + network);
        }
    }
    private void downloadManager() {
        DownloadManager manager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);

        String url = "https://get.jenkins.io/war-stable/2.319.2/jenkins.war";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Title");
        request.setDescription("Weird Description");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        String filename = URLUtil.guessFileName(url,null, MimeTypeMap.getFileExtensionFromUrl(url));

        //Uri uri = Uri.parse("file:///data/media/0/Android/data/com.aptiv.got.downloadmgr/files/" + filename);
        //Log.e(TAG, "getFilesDir=" + getFilesDir());
        //Log.e(TAG, "getExternalFilesDir=" + getExternalFilesDir("test"));
        //Log.e(TAG, "getExternalFilesDirs=" + getExternalFilesDirs("test")[0]);

        for(File f : this.getExternalFilesDirs(null)) {
            Log.e(TAG, "path=" + f);
        }

        // Emulated storage
        //request.setDestinationInExternalFilesDir(this, "/data/oem_data/", filename);

        // Unsupported part
        //Uri uri = Uri.parse("file:///data/oem_data/" + File.separator + filename);
        //request.setDestinationUri(uri);
        File f = new File("/data/oem_data");
        Log.e(TAG, "aaa=" + f.exists());
        File file = new File("/data/oem_data/", filename);
        request.setDestinationUri(Uri.fromFile(file));

        manager.enqueue(request);
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
    private void mediaSession() {
        MediaSessionManager sessionManager = (MediaSessionManager)getSystemService(Context.MEDIA_SESSION_SERVICE);
        List<MediaController> list = sessionManager.getActiveSessions(null);
        for(MediaController l : list) {
            Log.e(TAG, "" + l);
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
        /*for(StorageVolume volume : storage.getRecentStorageVolumes()) {
            Log.e(TAG, "volume=" + volume);
        }*/
    }
    private void systemProperties() {
        String text = SystemProperties.get("ro.build.user");
        Log.e(TAG, "text=" + text);
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
}