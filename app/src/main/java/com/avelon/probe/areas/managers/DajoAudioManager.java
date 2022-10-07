package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;

import com.avelon.probe.areas.AbstractManager;

public class DajoAudioManager extends AbstractManager {
    private AudioManager manager;
    public static String[] permissions = new String[] {};

    public DajoAudioManager(Context ctx) throws Exception {
        super(ctx, permissions);

        manager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void orchestrate() throws Exception {
        manager.getActivePlaybackConfigurations().forEach(config -> {
            Log.i(TAG, "playback=" + config);
        });

        manager.getActiveRecordingConfigurations().forEach(config -> {
            Log.i(TAG, "recording=" + config);
        });
        for(AudioDeviceInfo device : manager.getDevices(AudioManager.GET_DEVICES_ALL)) {
            Log.i(TAG, "device=" + device.getAddress());
            Log.i(TAG, "device=" + device.getProductName());
        }
    }
}
