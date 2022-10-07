package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.avelon.probe.areas.AbstractManager;

public class DajoUpdateManager extends AbstractManager {
    //private UpdateEngine manager;
    public static String[] permissions = new String[] {};

    public DajoUpdateManager(Context ctx) throws Exception {
        super(ctx, permissions);

       // manager = new UpdateEngine();
    }

    @Override
    public void orchestrate() throws Exception {
        // UpdateEngine updateEngine = new UpdateEngine();
        //Log.e(TAG, "updateEngine=" + updateEngine);
       /*
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
}
