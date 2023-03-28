package com.avelon.probe.areas.managers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.avelon.probe.R;
import com.avelon.probe.areas.AbstractManager;

public class DajoNotificationManager extends AbstractManager {
    private NotificationManager manager;
    public static String[] permissions = new String[] {};

    public DajoNotificationManager(Context ctx) throws Exception {
        super(DajoNotificationManager.class, ctx, permissions);

        /* Manager */
        manager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        /* Listeners */
    }

    @SuppressLint("MissingPermission")
    @Override
    public void orchestrate() throws Exception {

        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "channel_name", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("channel_desc");
        manager.createNotificationChannel(channel);


        Log.i(TAG, "Test notification...");
        Notification.Builder builder = new Notification.Builder(ctx)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("content")
                .setContentTitle("title")
                .setPriority(Notification.PRIORITY_HIGH)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setChannelId("CHANNEL_ID");

        Notification notfication = builder.build();

        manager.notify(1, notfication);

    }
}
