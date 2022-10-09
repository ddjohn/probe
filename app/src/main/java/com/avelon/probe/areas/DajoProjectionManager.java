package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.projection.MediaProjectionManager;

public class DajoProjectionManager extends AbstractManager {
    public static final int INT = 555;
    private MediaProjectionManager manager;
    public static String[] permissions = new String[] {};

    public DajoProjectionManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (MediaProjectionManager)ctx.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    public void orchestrate() throws Exception {
        ((Activity)ctx).startActivityForResult(manager.createScreenCaptureIntent(), INT);
    }
}
