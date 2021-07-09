package com.example.kanthi.projectmonitoring.Patroling;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import com.example.kanthi.projectmonitoring.Utils.AppPreferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Kanthi on 7/01/2019.
 */

public class GlobalApp extends Application implements Application.ActivityLifecycleCallbacks {
    public static Activity appactivity;
    public static MyReceiver receiver;
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        appactivity = activity;//here we get the activity
        Intent i = new Intent(this, MyReceiver.class);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        sendBroadcast(i);
        receiver = new MyReceiver();
        registerReceiver(receiver,intentFilter);

    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (receiver != null){
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public void createLocationRequest(Context context) {

        //UPDATE_INTERVAL_IN_MILLISECONDS for time to fetch location
        long UPDATE_INTERVAL_IN_MILLISECONDS = AppPreferences.getTimeInterval(context) * 1000;
        long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        ////////setSmallestDisplacement for distance in map/////////
        mLocationRequest.setSmallestDisplacement(2);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        requestLocationUpdates(context);
    }

    public void requestLocationUpdates(Context context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent(context));
        } catch (SecurityException e) {
            e.printStackTrace();
            if (Build.VERSION.SDK_INT >= 26) {
                Intent service = new Intent(context,Notification_Service.class);
                if (Notification_Service.IS_SERVICE_RUNNING) {
                    service.setAction("stopforeground");
                    Notification_Service.IS_SERVICE_RUNNING = true;
                    startForegroundService(service);
                }
            }
        }
    }

    public void removeLocationUpdates(Context context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.removeLocationUpdates(getPendingIntent(context));
    }

    public PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MyReceiver.class);
        intent.setAction(MyReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

