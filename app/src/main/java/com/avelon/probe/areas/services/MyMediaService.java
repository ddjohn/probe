package com.avelon.probe.areas.services;

import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyMediaService extends MediaBrowserService {
    private static final String TAG = MyMediaService.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Creating MediaBroswerService");

        MediaSession mediaSession = new MediaSession(this, "TOKEN");
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(new MediaSession.Callback() {

        });

        PlaybackState state = new PlaybackState.Builder().setActions(
                PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE  |
                        PlaybackState.ACTION_STOP  | PlaybackState.ACTION_SKIP_TO_NEXT |
                        PlaybackState.ACTION_SKIP_TO_PREVIOUS | PlaybackState.ACTION_FAST_FORWARD |
                        PlaybackState.ACTION_REWIND).build();
        mediaSession.setPlaybackState(state);
        Log.e(TAG, "session=" + mediaSession);

        MediaController controller = mediaSession.getController();
        Log.e(TAG, "controller=" + controller);

        setSessionToken(mediaSession.getSessionToken());
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("ddjohn", null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {

    }
}
