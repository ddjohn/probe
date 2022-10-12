package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class DajoTextToSpeech extends AbstractManager {
    public static final int REQUEST_CODE = 111;
    public static String[] permissions = new String[] {};

    private Context ctx;
    private TextToSpeech tts;

    public DajoTextToSpeech(Context ctx) throws Exception {
        super(ctx, permissions);

        this.ctx = ctx;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        Intent intent = new Intent();
        intent.setAction(android.speech.tts.TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        ((Activity)ctx).startActivityForResult(intent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult()");

         tts = new TextToSpeech(ctx, status -> {
             int res = tts.setLanguage(Locale.ENGLISH);
             Log.e(TAG, "res=" + res);

             tts.speak(
                     "Magnus, see you at the airport!!",
                     TextToSpeech.QUEUE_ADD,
                     null);
         });
    }
}
