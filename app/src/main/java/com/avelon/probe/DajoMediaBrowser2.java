package com.avelon.probe;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DajoMediaBrowser2 extends MediaBrowserService {
    private static final String TAG = DajoMediaBrowser2.class.getSimpleName();

    private DajoMediaSession session;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
        session = new DajoMediaSession(this, "numero2");
        this.setSessionToken(session.getToken());
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String s, int i, @Nullable Bundle bundle) {
        Log.e(TAG, "onLoadChildren()");
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowser.MediaItem>> result) {
        Log.e(TAG, "onLoadChildren()");
    }
}
