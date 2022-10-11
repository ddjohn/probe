package com.avelon.probe.areas.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoProjectionManager extends AbstractManager {
    public static final int REQUEST_CODE = 555;
    private MediaProjectionManager manager;
    public static String[] permissions = new String[] {};
    private Context ctx;

    public DajoProjectionManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (MediaProjectionManager)ctx.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        this.ctx = ctx;
    }

    @Override
    public void orchestrate() throws Exception {
        ((Activity)ctx).startActivityForResult(manager.createScreenCaptureIntent(), REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()");

        Handler handler = new Handler(Looper.getMainLooper());

        MediaProjection mediaProjection = manager.getMediaProjection(resultCode, data);
        ImageReader imageReader = ImageReader.newInstance(640, 400, ImageFormat.JPEG, 2);
        mediaProjection.createVirtualDisplay("screencap", 640, 360, ctx.getResources().getDisplayMetrics().densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY,
                imageReader.getSurface(), new VirtualDisplay.Callback() {
                    //Log.e(TAG, "callback");
                }, handler);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader imageReader) {
                Log.e(TAG, "onImageAvailable()");
            }
        }, handler);
    }
}
