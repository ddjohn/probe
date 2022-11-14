package com.avelon.probe.areas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public abstract class AbstractManager {
    protected final Context ctx;
    //protected final static String TAG = AbstractManager.class.getSimpleName(); //.getCanonicalName();
    protected String TAG = "not-set";

    public AbstractManager(Context ctx, String[] permissions) throws Exception {
        this.ctx = ctx;

        TAG = ctx.getClass().getSimpleName();

        if(!neededPermissions(permissions))
            throw new Exception("Needed permissions not granted");
    }

    public abstract void orchestrate() throws Exception;

    protected void checkFeature(String feature) throws NoFeatureException {
        if(!ctx.getPackageManager().hasSystemFeature(feature)) {
            throw new NoFeatureException("No feature: " + feature);
        }
    }

    protected boolean neededPermissions(String[] permissions) {
        for(String permission : permissions) {
            if (ctx.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Not approved permission: " + permission);
                return false;
            }
        }
        return true;
    }

    public class NoFeatureException extends Exception {
        public NoFeatureException(String s) {
            super(s);
        }
    }
}
