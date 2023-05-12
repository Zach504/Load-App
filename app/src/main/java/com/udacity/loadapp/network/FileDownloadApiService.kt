package com.udacity.loadapp.network

import android.content.Context
import com.udacity.loadapp.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

object FileDownloadApi {
    val client by lazy { OkHttpClient() }

    suspend fun getFile(urlString: String, applicationContext: Context): Status {
        var status = Status.FAILURE
        val  url = URL(urlString)
        val request = Request.Builder().url(url).build()
        var code = -1
        try {
            val response = client.newCall(request).execute()
            if(response.code == 200){
                status = Status.SUCCESS
                //Save File
                withContext(Dispatchers.IO) {
                    val file = File(applicationContext.filesDir,System.currentTimeMillis().toString() + ".zip")
                    val outputStream = FileOutputStream(file)
                    outputStream.write(response.body?.bytes())
                    outputStream.close()
                }
            }
            response.close()
        }
        catch (e: IOException){
            return status
        }
        return status

    }

}