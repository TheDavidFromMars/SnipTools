package com.jaqxues.sniptools.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.networking.ServerApk
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.sendAsRequest
import java.io.File
import javax.inject.Inject

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.11.20 - Time 13:01.
 */
class ApkRepository @Inject constructor(
    private val apiService: GitHubApiService
) {
    /**
     * Return null if current apk is latest, else return name of downloaded Apk
     */
    suspend fun downloadLatestApk(context: Context): File? {
        val latestApk = apiService.getLatestApk()
        if (latestApk.versionName == BuildConfig.VERSION_NAME && latestApk.versionCode == BuildConfig.VERSION_CODE) {
            return null
        }
        val file = File(context.filesDir, "${latestApk.name}.apk")
        apiService.getApkFile(latestApk.name).byteStream().use {
            file.outputStream().use(it::copyTo)
        }
        return file
    }
}