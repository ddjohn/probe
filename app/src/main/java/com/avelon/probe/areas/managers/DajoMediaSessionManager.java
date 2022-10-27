package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.util.Log;
import com.avelon.probe.areas.AbstractManager;
import java.util.List;

public class DajoMediaSessionManager extends AbstractManager {
    private MediaSessionManager manager;
    public static String[] permissions = new String[] {};

    public DajoMediaSessionManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (MediaSessionManager) ctx.getSystemService(Context.MEDIA_SESSION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        List<MediaController> list = manager.getActiveSessions(null);
        for(MediaController l : list) {
            Log.e(TAG, "media controller: " + l);
            Bundle b = l.getExtras();
            String devicename = (String)b.get("DEVICE_NAME");
        }
    }
}
