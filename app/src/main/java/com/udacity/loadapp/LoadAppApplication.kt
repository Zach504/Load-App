package com.udacity.loadapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build

class LoadAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //Initialize Notification Channels
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java) as NotificationManager
        if(notificationManager.getNotificationChannel(applicationContext.getString(R.string.download_notification_channel_id)) == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    applicationContext.getString(R.string.download_notification_channel_id),
                    applicationContext.getString(R.string.download_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    setShowBadge(false)
                }
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationChannel.description = "It's Download Time"
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

    }
}