package com.herocorp.ui.FCMservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.herocorp.R;

import com.herocorp.ui.activities.BaseDrawerActivity;
import com.herocorp.ui.activities.auth.SignInActivity;
import com.herocorp.ui.utility.ClearCache;
import com.herocorp.ui.utility.PreferenceUtil;


// Created by rsawh on 09-Oct-16.


public class FCMmessageservice extends FirebaseMessagingService {
    private static final String TAG = "heroFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Data: " + remoteMessage.getData().get("Title") + remoteMessage.getData().get("Body") + remoteMessage.getData().get("tag"));


        //create notification
        String title = "Hero Feeds", tag = "news";
        try {
            if (remoteMessage.getData().get("Title") != null) {
                title = remoteMessage.getData().get("Title");
            }
            if (remoteMessage.getData().get("tag") != null) {
                tag = remoteMessage.getData().get("tag");
            }
        } catch (Exception e) {
            Log.e("notif_error", e.toString());
        }
        createNotification(title, remoteMessage.getData().get("Body"), tag);

    }

    private void createNotification(String title, String messageBody, String tag) {
        try {
            if (tag.equalsIgnoreCase("cache"))
                ClearCache.deleteCache(this);
            Intent resultIntent = new Intent(this, SignInActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            resultIntent.putExtras(bundle);
            int requestCode = 0;//Your request code
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //   resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            String message = messageBody;
            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.hero_logo)
                    .setContentTitle(title)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.hero_logo))
                    .setContentText(message)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            noBuilder.build().flags |= Notification.FLAG_NO_CLEAR;
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build());

        } catch (Exception e) {
        }
    }
}
