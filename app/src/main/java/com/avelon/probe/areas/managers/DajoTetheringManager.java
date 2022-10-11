package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Network;
import android.net.TetheredClient;
import android.net.TetheringManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

public class DajoTetheringManager extends AbstractManager {
    private TetheringManager manager;
    public static String[] permissions = new String[] {};

    public DajoTetheringManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (TetheringManager) ctx.getSystemService("tethering" /*Context.TETHERING_SERVICE*/);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {

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
    }
}
