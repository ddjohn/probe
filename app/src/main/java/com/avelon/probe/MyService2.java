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

    @SuppressLint({"MissingPermission", "Range"})
    @Override
    public void onCreate() {
        super.onCreate();

//new MQTT(this);
        //new RecursiveScan(this, "/data/media/10/Android/data/com.aptiv.got.downloadmgr/files/");
        //new WebServer(this);
        EncodeAndMux mux = new EncodeAndMux(this.getFilesDir());
        mux.testEncodeVideoToMp4();
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