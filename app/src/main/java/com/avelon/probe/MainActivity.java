package com.avelon.probe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Network;
import android.net.TetheredClient;
import android.net.TetheringManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.os.SystemProperties;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.WindowManager;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    MediaProjectionManager mediaProjectionManager;

//    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, "android.permission.TETHER_PRIVILEGED"/*Manifest.permission.TETHER_PRIVILEGED*/) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "1Not approved");
        }
        else {
            Log.e(TAG, "1Approved");
        }

        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "2Not approved");
        }
        else {
            Log.e(TAG, "2Approved");
        }

        if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_SETTINGS") != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "3Not approved");
        }
        else {
            Log.e(TAG, "3Approved");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "4Not approved");
        }
        else {
            Log.e(TAG, "4Approved");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "5Not approved");
        }
        else {
            Log.e(TAG, "5Approved");
        }

        @SuppressLint("WrongConstant") TetheringManager manager = (TetheringManager)this.getSystemService("tethering" /*Context.TETHERING_SERVICE*/);
        manager.registerTetheringEventCallback(Executors.newSingleThreadExecutor(), new TetheringManager.TetheringEventCallback() {
                    @Override
                    public void onTetheringSupported(boolean supported) {
                        Log.e(TAG, "onTetheringSupported(): " + supported);
                        TetheringManager.TetheringEventCallback.super.onTetheringSupported(supported);
                    }

                    @Override
                    public void onUpstreamChanged(Network network) {
                        Log.e(TAG, "onUpstreamChanged(): " + network);
                        TetheringManager.TetheringEventCallback.super.onUpstreamChanged(network);
                    }

                    @Override
                    public void onTetherableInterfaceRegexpsChanged(TetheringManager.TetheringInterfaceRegexps reg) {
                        Log.e(TAG, "onTetherableInterfaceRegexpsChanged(): " + reg);
                        TetheringManager.TetheringEventCallback.super.onTetherableInterfaceRegexpsChanged(reg);
                    }

                    @Override
                    public void onTetherableInterfacesChanged(List<String> interfaces) {
                        Log.e(TAG, "onTetherableInterfacesChanged(): " + interfaces);
                        TetheringManager.TetheringEventCallback.super.onTetherableInterfacesChanged(interfaces);
                    }

                    @Override
                    public void onTetheredInterfacesChanged(List<String> interfaces) {
                        Log.e(TAG, "onTetheredInterfacesChanged(): " + interfaces);
                        TetheringManager.TetheringEventCallback.super.onTetheredInterfacesChanged(interfaces);
                    }

                    @Override
                    public void onError(String ifName, int error) {
                        Log.e(TAG, "onError(): " + ifName + error);
                        TetheringManager.TetheringEventCallback.super.onError(ifName, error);
                    }

                    @Override
                    public void onClientsChanged(Collection<TetheredClient> clients) {
                        Log.e(TAG, "onClientsChanged(): " + clients);
                        TetheringManager.TetheringEventCallback.super.onClientsChanged(clients);

                        clients.forEach(client -> {
                            Log.e(TAG, "client=" + client);
                        });
                    }

                    @Override
                    public void onOffloadStatusChanged(int status) {
                        Log.e(TAG, "onOffloadStatusChanged(): " + status);
                        TetheringManager.TetheringEventCallback.super.onOffloadStatusChanged(status);
                    }
                }
        );
        Log.e(TAG, "Tethering=" + manager);


        //TetheringEventCallback::onClientsChanged()
/*
        try {
            MutualTlsServer server = new MutualTlsServer(this);
            MutualTlsClient client = new MutualTlsClient(this);
        }
        catch (Exception e) {
           Log.e(TAG, "exception", e);
        }
*/
/*
        MyReceiver receiver = new MyReceiver();
        for(Field field : Intent.class.getDeclaredFields()) {
            String name = field.getName();
            if(name.startsWith("ACTION_")) {
                Log.e(TAG, "Register android.intent.action." + name);
                registerReceiver(receiver, new IntentFilter("android.intent.action." + name));
            }
        }
*/
        //startService(new Intent(this, MyService.class));

        //Log.e(TAG, "Requesting projection");
        // mediaProjectionManager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        //startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 666);

        //Log.e("MUNGO", "" + BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);

        if (requestPermissions()) return;

        //adapter.enable();

        //final TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);

        // On, Off, Disabled, Limited (reverse camera, climate)


        // PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(this.getApplicationContext(), MyService.class), "example");
        //PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "example").setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER).build();
        //PhoneAccountHandle phoneAccountHandle = telecomManager.getDefaultOutgoingPhoneAccount("ytetet");
        //Bundle extras = new Bundle();
        //extras.putParcelable(TelecomManager, uri);

        //TelecomManager telecom = (TelecomManager)getSystemService(Context.TELECOM_SERVICE);
        //telecom.addNewIncomingCall(phoneAccountHandle, extras);

    }

    private boolean requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        Handler handler = new Handler(Looper.getMainLooper());

        MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        ImageReader imageReader = ImageReader.newInstance(640, 400, ImageFormat.JPEG, 2);
        mediaProjection.createVirtualDisplay("screencap", 640, 360, getResources().getDisplayMetrics().densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY,
                imageReader.getSurface(), new VirtualDisplay.Callback() {
                }, handler);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader imageReader) {
                Log.e(TAG, "onImageAvailable()");
            }
        }, handler);
    }
}