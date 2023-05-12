package com.udacity.loadapp.viewmodels

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.loadapp.*
import com.udacity.loadapp.network.FileDownloadApi
import com.udacity.loadapp.notifications.sendDownloadCompleteNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var selectedDownloadOption = MutableLiveData<Int>()
    var downloadInProgress = MutableLiveData<Boolean>()

    init {
        downloadInProgress.value = false
    }

    fun getFile(){
        downloadInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val applicationContext = getApplication<Application>().applicationContext
            var url = ""
            var target = ""
            when(selectedDownloadOption.value){
                R.id.glideRadioButton -> {
                    url = GLIDE_PATH
                    target = applicationContext.getString(R.string.glide_download)
                }
                R.id.starterRadioButton -> {
                    url = STARTER_PROJECT_PATH
                    target = applicationContext.getString(R.string.project_download)
                }
                R.id.retrofitRadioButton -> {
                    url = RETROFIT_PATH
                    target = applicationContext.getString(R.string.retrofit_download)
                }
                else -> {
                    url = GLIDE_PATH
                    target = applicationContext.getString(R.string.glide_download)
                }
            }
            val status = FileDownloadApi.getFile(url, applicationContext)
            var statusString = applicationContext.getString(R.string.failure)
            if(status == Status.SUCCESS) {
                statusString = applicationContext.getString(R.string.success)
            }
            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.sendDownloadCompleteNotification(target, statusString, getApplication<Application>().applicationContext)
            withContext(Dispatchers.Main){
                downloadInProgress.value = false
            }
        }
    }

}