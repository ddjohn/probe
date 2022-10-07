package com.avelon.probe;

import android.content.Context;
import android.util.Log;

import com.avelon.probe.areas.concepts.MutualTlsClient;
import com.avelon.probe.areas.concepts.MutualTlsServer;

public class MyConcepts {
    private static final String TAG = MyConcepts.class.getSimpleName();
    private Context ctx;

    public MyConcepts(Context ctx) {
        this.ctx = ctx;
    }

    public void init() {
        Log.e(TAG, "init()");
        try {
            MutualTlsServer server = new MutualTlsServer(ctx);
            MutualTlsClient client = new MutualTlsClient(ctx);
        }
        catch (Exception e) {
            Log.e(TAG, "exception", e);
        }

    }
}
