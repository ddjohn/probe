package com.avelon.probe;

import android.annotation.SuppressLint;
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
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.IBinder;
//import android.os.SystemProperties;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;
import com.avelon.probe.areas.managers.DajoAlarmManager;
import com.avelon.probe.areas.DajoAlertDialog;
import com.avelon.probe.areas.managers.DajoBluetoothManager;
import com.avelon.probe.areas.managers.DajoCarPropertyManager;
import com.avelon.probe.areas.managers.DajoProjectionManager;
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
    private DajoProjectionManager projection;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            AbstractManager[] managers = {
                    new DajoAccountManager(this),
                    new DajoActivityManager(this),
                    new DajoActivityManager(this),
                    new DajoAlarmManager(this),
                    new DajoAlertDialog(this),
                    new DajoBluetoothManager(this),
                    new DajoBuild(this),
                    new DajoCarPropertyManager(this),
                    //new DajoCarNavigationStatusManager(this),
                    new DajoConnectivityManager(this),
                    new DajoDownloadManager(this),
                    new DajoDropboxManager(this),
                    new DajoEnvironment(this),
                    new DajoKeyStore(this),
                    //new DajoLocale(this),
                    new DajoLocationManager(this),
                    new DajoPackageManager(this),
                    projection = new DajoProjectionManager(this),
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

        //carNavigationStatusManager();
        //mediaSession();

        Log.e(TAG, "-- END --");
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