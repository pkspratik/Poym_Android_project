package com.example.kanthi.projectmonitoring.Patroling;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.kanthi.projectmonitoring.R;

import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;

public class Notification_Service extends Service {

    private static final String LOG_TAG = "ForegroundService";
    public static boolean IS_SERVICE_RUNNING = false;
    NotificationCompat.Builder notify;

    public Notification_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        ///open screen when we click on notification
        Intent notificationIntent = new Intent(this, Patroling_Activity.class);
        notificationIntent.setAction("action.main");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("LocationService","trackingService", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Tracking.....");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notify = new NotificationCompat.Builder(this, "LocationService");
            notify.setContentTitle("POYM")
                    .setContentText("Location service running")
                    .setSmallIcon(R.drawable.poym_logo)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setOngoing(true).build();
        } else {
            notify = new NotificationCompat.Builder(this, "LocationService");
            notify.setContentTitle("POYM")
                    .setContentText("Location service running")
                    .setSmallIcon(R.drawable.poym_logo)
                    .setContentIntent(pendingIntent)
                    .setPriority(PRIORITY_MAX)
                    .setOngoing(true);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            if (intent.getAction().equals("startforeground")) {
                Log.i(LOG_TAG, "Received Start Foreground Intent ");
                startForeground(101, notify.build());
            } else if (intent.getAction().equals("stopforeground")) {
                stopForeground(true);
                stopSelf();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
