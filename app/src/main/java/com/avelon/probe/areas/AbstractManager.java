package com.avelon.probe.areas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public abstract class AbstractManager {
    protected final Context ctx;
    protected final static String TAG = AbstractManager.class.getCanonicalName();

    public AbstractManager(Context ctx, String[] permissions) throws Exception {
        this.ctx = ctx;
        if(!neededPermissions(permissions))
            throw new Exception("Needed permissions not granted");
    }

    public abstract void orchestrate() throws Exception;

    protected boolean neededPermissions(String[] permissions) {
        for(String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Not approved permission: " + permission);
                return false;
            }
        }
        return true;
    }
}
