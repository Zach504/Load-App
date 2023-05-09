package com.udacity.loadapp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.loadapp.DetailActivity
import com.udacity.loadapp.R

private val NOTIFICATION_ID = 22

fun NotificationManager.sendDownloadCompleteNotification(target: String, status: String, applicationContext: Context) {
    //Create intent to direct to activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    //Create pending intent
    val contentPendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_IMMUTABLE)
    //Styling
    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.download_notification_channel_id))
        .setSmallIcon(R.drawable.baseline_cloud_circle_24)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    //Notify
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}