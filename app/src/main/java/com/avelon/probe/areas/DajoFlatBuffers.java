package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.concepts.EncodeAndMux;
import com.avelon.probe.concepts.FlatBuffers;

public class DajoFlatBuffers extends AbstractManager {
    private FlatBuffers flatBuffers;
    public static String[] permissions = new String[] {};

    public DajoFlatBuffers(Context ctx) throws Exception {
        super(ctx, permissions);

        //manager = (TemplateManager) ctx.getSystemService(Context.TEMPLATE_SERVICE);
        flatBuffers = new FlatBuffers(ctx);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        try {

        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }
    }
}
