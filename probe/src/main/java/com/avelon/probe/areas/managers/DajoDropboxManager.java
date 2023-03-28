package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoDropboxManager extends AbstractManager {
    private DropBoxManager manager;
    public static String[] permissions = new String[] {};

    public DajoDropboxManager(Context ctx) throws Exception {
        super(DajoDropboxManager.class, ctx, permissions);

        manager = (DropBoxManager)ctx.getSystemService(Context.DROPBOX_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        manager.addText("author", "david");
        // /data/system/dropbox

        Log.i(TAG, "dropbox=" + manager.getNextEntry("data_app_crash", 1000));
        DropBoxManager.Entry entry =  manager.getNextEntry("data_app_crash", 1000);
        Log.i(TAG, "text=" + entry.getText(100));

        for(int i = 0; i < 10; i++) {
            Log.i(TAG, "==>" + manager.getNextEntry("data_app_crash", 1000).getText(500));
        }

        Log.i(TAG, "==>" + manager.getNextEntry("system_server_wtf", 1000).getText(500));
    }
}
