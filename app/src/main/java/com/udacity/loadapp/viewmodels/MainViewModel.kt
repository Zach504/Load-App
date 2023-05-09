package com.udacity.loadapp.viewmodels

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.loadapp.GLIDE_PATH
import com.udacity.loadapp.R
import com.udacity.loadapp.network.FileDownloadApi
import com.udacity.loadapp.notifications.sendDownloadCompleteNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var selectedDownloadOption = MutableLiveData<Int>()

    fun getFile(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = FileDownloadApi.getFile(GLIDE_PATH)
            var status = "Failure"
            if(response != null){
                if(response.code == 200){
                    status = "Success"
                }
            }
            val applicationContext = getApplication<Application>().applicationContext
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
            notificationManager.sendDownloadCompleteNotification("Glide", status, getApplication<Application>().applicationContext)


        }
    }

}