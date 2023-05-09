package com.udacity.loadapp.network

import android.os.Environment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.IOException
import java.net.URL

object FileDownloadApi {
    val client by lazy { OkHttpClient() }

    suspend fun getFile(urlString: String): Response? {
        val  url = URL(urlString)
        val request = Request.Builder().url(url).build()
        var code = -1
        try {
            val response = client.newCall(request).execute()
            return response
        }
        catch (e: IOException){

        }
        return null

    }

}