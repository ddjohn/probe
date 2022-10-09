package com.avelon.probe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.browse.MediaBrowser;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Network;
//import android.net.TetheredClient;
//import android.net.TetheringManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.os.SystemProperties;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.WindowManager;

import com.avelon.probe.areas.DajoProjectionManager;
import com.avelon.probe.areas.MyMediaService;

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

    private MyPermissions permissions;

    MediaBrowser mediaBrowser = null;

    //MediaProjectionManager mediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MediaProjectionManager manager = (MediaProjectionManager)getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(manager.createScreenCaptureIntent(), 444);

        try {
            new DajoProjectionManager(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Checking Permissions
        permissions = new MyPermissions(this);
        permissions.request();
        if(permissions.check()) {
            Log.e(TAG, "Missing permission - skipping!");
            return;
        }

        // Receivers
        //MyReceiver receiver = new MyReceiver(this);

        // Concepts
        MyConcepts concepts = new MyConcepts(this);
        //concepts.init();

        // Starting Services
        //startService(new Intent(this, MyService.class));

        /*
        @SuppressLint("WrongConstant") TetheringManager manager = (TetheringManager)this.getSystemService("tethering"); //Context.TETHERING_SERVICE);
        manager.registerTetheringEventCallback(Executors.newSingleThreadExecutor(), new TetheringManager.TetheringEventCallback() {
                    @Override
                    public void onTetheringSupported(boolean supported) {
                        Log.e(TAG, "onTetheringSupported(): " + supported);
                        TetheringManager.TetheringEventCallback.super.onTetheringSupported(supported);
                    }

                    @Override
                    public void onUpstreamChanged(Network network) {
                        Log.e(TAG, "onUpstreamChanged(): " + network);
                        TetheringManager.TetheringEventCallback.super.onUppackage:mine ANDstreamChanged(network);
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
*/

        //TetheringEventCallback::onClientsChanged()


        mediaBrowser = new MediaBrowser(this,
                new ComponentName(this, MyMediaService.class),
                new MediaBrowser.ConnectionCallback() {
                    @Override
                    public void onConnected() {
                        super.onConnected();

                        MediaSession.Token token = mediaBrowser.getSessionToken();
                        Log.e(TAG, "token=" + token);

                        MediaController mediaController = new MediaController(MainActivity.this, token);
                        Log.e(TAG, "controller=" + mediaController);


                        String root = mediaBrowser.getRoot();
                        Log.i(TAG, "MediaRoot=" + root);

                    }
                },
                null);
        Log.e(TAG, "browser=" + mediaBrowser);

    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e(TAG, "connect to browsing service");
//        mediaBrowser.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case MyPermissions.REQUEST_CODE: {
                permissions.onActivityResult(requestCode, resultCode, data);
                break;
            }
            default: {
                Log.e(TAG, "Unknown request code: " + requestCode);
            }
        }

        /*
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
        }, handler);*/
    }
}