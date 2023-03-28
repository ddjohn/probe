package com.avelon.probe;

import android.content.Context;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.util.Log;

public class DajoMediaSession extends MediaSession.Callback {
    private static final String TAG = DajoMediaSession.class.getSimpleName();

    private final MediaSession session;

    public DajoMediaSession(Context ctx, String token) {
        Log.e(TAG, "DajoMediaSession()");
        session = new MediaSession(ctx, token);

        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        session.setCallback(this);

        PlaybackState state = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY             | PlaybackState.ACTION_PAUSE        |
                            PlaybackState.ACTION_STOP             | PlaybackState.ACTION_SKIP_TO_NEXT |
                            PlaybackState.ACTION_SKIP_TO_PREVIOUS | PlaybackState.ACTION_FAST_FORWARD |
                            PlaybackState.ACTION_REWIND).build();
        session.setPlaybackState(state);
        session.setPlaybackToRemote(new VolumeProvider(VolumeProvider.VOLUME_CONTROL_ABSOLUTE, 10, 2) {
            @Override
            public void onSetVolumeTo(int volume) {
                Log.e(TAG, "onSetVolume(): " + volume);
                super.onSetVolumeTo(volume);
            }
        });
        session.setActive(true);
    }

    @Override
    public void onPlay() {
        Log.e(TAG, "onPlay(): " + session.getSessionToken());
        super.onPlay();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause(): " + session.getSessionToken());
        super.onPause();
    }

    public MediaSession.Token getToken() {
        return this.session.getSessionToken();
    }
}