package com.uni.bau.services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uni.bau.R;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.ui.Activites.SplashActivity;


import java.util.Map;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String token) {
        SharedPreferencesHelper.putSharedPreferencesString(getBaseContext(), ApiConstants.notificationKey,token);

        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Map<String, String> data = remoteMessage.getData();
            if (remoteMessage.getNotification() != null) {
                sendNotificationObject(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            }else {
                sendNotificationObject(data.get("title"), data.get("body"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void sendNotificationObject(String title, String messageBody) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(this, SplashActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
            PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 1;
            String channelId = "ufa";
            String channelName = "ufa";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.drawable.uni_app_icon)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setPriority(PRIORITY_MAX)
                    .setTicker(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);
            notificationManager.notify(notificationId, mBuilder.build());
        } else {

            NotificationManager nManager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intentNotification = new Intent(this, SplashActivity.class);

            intentNotification.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 50, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = null;
            notificationBuilder = new NotificationCompat.Builder(this.getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.uni_app_icon)
                    .setLights(Color.LTGRAY, 1000, 1000)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setPriority(PRIORITY_MAX)
                    .setTicker(title)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            Notification notification = notificationBuilder.build();
            notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
            nManager.notify((int) SystemClock.currentThreadTimeMillis(), notification);
        }
    }


}
