package com.avelon.probe;

import android.app.Activity;
import android.media.browse.MediaBrowser;
import android.util.Log;

import com.avelon.probe.areas.concepts.qrcode.MyQRCode;
import com.avelon.probe.areas.concepts.qrcode.MyQRGEncoder;
import com.avelon.probe.areas.concepts.tls.MutualTlsClient;
import com.avelon.probe.areas.concepts.tls.MutualTlsServer;

public class MyConcepts {
    private static final String TAG = MyConcepts.class.getSimpleName();
    private Activity ctx;
    MediaBrowser browser1;
    MediaBrowser browser2;

    public MyConcepts(Activity ctx) {
        this.ctx = ctx;
    }

    public void init() {
        Log.e(TAG, "init()");
        try {
            MutualTlsServer server = new MutualTlsServer(ctx);
            MutualTlsClient client = new MutualTlsClient(ctx);

            MyQRCode qrcode1 = new MyQRCode(ctx);
            MyQRGEncoder qrcode2 = new MyQRGEncoder(ctx);

            /*
            // MQTT(ctx);
            new RecursiveScan(ctx, "/data/media/10/Android/data/com.aptiv.got.downloadmgr/files/");
            new WebServer(ctx);
            EncodeAndMux mux = new EncodeAndMux(ctx.getFilesDir());
            mux.testEncodeVideoToMp4();
             */
        }
        catch (Exception e) {
            Log.e(TAG, "exception", e);
        }


    }
}
