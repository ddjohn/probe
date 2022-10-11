package com.avelon.probe;

import android.content.Context;
import android.util.Log;
import com.avelon.probe.areas.concepts.MutualTlsClient;
import com.avelon.probe.areas.concepts.MutualTlsServer;
import com.avelon.probe.concepts.EncodeAndMux;
import com.avelon.probe.concepts.MQTT;
import com.avelon.probe.concepts.RecursiveScan;
import com.avelon.probe.concepts.WebServer;

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

            new MQTT(ctx);
            new RecursiveScan(ctx, "/data/media/10/Android/data/com.aptiv.got.downloadmgr/files/");
            new WebServer(ctx);
            EncodeAndMux mux = new EncodeAndMux(ctx.getFilesDir());
            mux.testEncodeVideoToMp4();
        }
        catch (Exception e) {
            Log.e(TAG, "exception", e);
        }
    }
}
