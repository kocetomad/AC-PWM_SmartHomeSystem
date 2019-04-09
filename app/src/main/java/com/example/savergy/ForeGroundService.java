package com.example.savergy;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;



import org.w3c.dom.Text;

import java.net.URISyntaxException;

import static android.support.v4.app.NotificationCompat.PRIORITY_MIN;
import static android.support.v4.app.NotificationCompat.getChannelId;


/**
 *
 * Created by roberto on 9/29/16.
 */

public class ForeGroundService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        super.onStartCommand(intent, flags, startId);

        String NOTIFICATION_CHANNEL_ID = "pepega";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        Notification notification = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("SmartHome and alarm service running")
                .setContentText("pepga")
                .setSmallIcon(R.drawable.ic_bluetooth_connected_black_24dp)
                .build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }
    @Override
    public void onCreate() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);


    }


    private void attemptSend(String location) {
        if (TextUtils.isEmpty(location)) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}