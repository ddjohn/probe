package com.avelon.probe;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import com.avelon.probe.areas.DajoProjectionManager;
import com.avelon.probe.areas.MyMediaService;

public class MainActivity extends MyActivityLifecycle {
    private static final String TAG = MainActivity.class.getCanonicalName();

    private MyPermissions permissions;
    private DajoProjectionManager projection;

    MediaBrowser mediaBrowser = null;

    //MediaProjectionManager mediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try {
            projection = new DajoProjectionManager(this);
            //projection.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Activity")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

        // Checking Permissions
        permissions = new MyPermissions(this);
        permissions.request();
        if(permissions.check() == false) {
            Log.e(TAG, "Missing permission - skipping!");
            return;
        }

        // Receivers
        MyReceiver receiver = new MyReceiver(this);

        // Concepts
        MyConcepts concepts = new MyConcepts(this);
        //concepts.init();

        // Starting Services
       startService(new Intent(this, MyService.class));

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
        Log.e(TAG, "onActivityResult(): " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case MyPermissions.REQUEST_CODE: {
                permissions.onActivityResult(requestCode, resultCode, data);
                break;
            }
            case DajoProjectionManager.REQUEST_CODE: {
                projection.onActivityResult(requestCode, resultCode, data);
                break;
            }
            default: {
                Log.e(TAG, "Unknown request code: " + requestCode);
            }
        }
    }
}