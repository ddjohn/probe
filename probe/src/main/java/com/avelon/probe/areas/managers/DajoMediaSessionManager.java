package com.avelon.probe.areas.managers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.Session2Token;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.avelon.probe.MainActivity;
import com.avelon.probe.areas.AbstractManager;
import java.util.List;

public class DajoMediaSessionManager extends AbstractManager {
    private MediaSessionManager manager;
    public static String[] permissions = new String[] {Manifest.permission.MEDIA_CONTENT_CONTROL};

    public DajoMediaSessionManager(Context ctx) throws Exception {
        super(DajoMediaSessionManager.class, ctx, permissions);

        manager = (MediaSessionManager) ctx.getSystemService(Context.MEDIA_SESSION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {
        List<MediaController> controllers = manager.getActiveSessions(null);
        for(MediaController controller : controllers) {
            Log.e(TAG, "controller: " + controller.getPackageName() + "," + controller.getPlaybackState() + "," + controller.getPlaybackInfo());

            controller.registerCallback(new MediaController.Callback() {
                @Override
                public void onSessionEvent(@NonNull String event, @Nullable Bundle extras) {
                    super.onSessionEvent(event, extras);
                    Log.e(TAG, "onSessionEvent()");
                }

                @Override
                public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                    super.onMetadataChanged(metadata);
                    Log.e(TAG, "onMetadataChanged(): " + metadata.getString(MediaMetadata.METADATA_KEY_TITLE));
                    Log.e(TAG, "onMetadataChanged(): " + metadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
                }
            });
        }

        for (Session2Token session2Token : manager.getSession2Tokens()) {
            Log.e(TAG, "token: " + session2Token);
        }

        ComponentName componentName = new ComponentName(ctx, MainActivity.class.getName());
        manager.addOnActiveSessionsChangedListener(c -> {
            int size =  c.size();
            if(size > 0) {
                for(MediaController controller : c) {
                    Log.e(TAG, "onActiveSessionsChanged(): " + c.get(0).getPackageName());
                    MediaSession.Token token = controller.getSessionToken();
                    MediaController ctrl = new MediaController(ctx, token);

                    ctrl.registerCallback(new MediaController.Callback() {
                        @Override
                        public void onSessionEvent(@NonNull String event, @Nullable Bundle extras) {
                            super.onSessionEvent(event, extras);
                            Log.e(TAG, "onSessionEvent()");
                        }

                        @Override
                        public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                            super.onMetadataChanged(metadata);
                            Log.e(TAG, "onMetadataChanged(): " + metadata.getString(MediaMetadata.METADATA_KEY_TITLE));
                            Log.e(TAG, "onMetadataChanged(): " + metadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
                        }
                    });
                }
            } else {
                Log.e(TAG, "onActiveSessionsChanged(): " + controllers);
            }
        }, componentName);
    }
}
