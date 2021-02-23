package com.jaqxues.sniptools.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.repository.ApkRepository
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.SuUtils
import com.jaqxues.sniptools.utils.sendAsRequest
import com.topjohnwu.superuser.Shell
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.11.20 - Time 13:07.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val apkRepo: ApkRepository
) : ViewModel() {
    private val _downloadEvents = Channel<Request<File?>>()
    val downloadEvents = _downloadEvents.receiveAsFlow()

    fun downloadApk(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _downloadEvents sendAsRequest {
                apkRepo.downloadLatestApk(context)
            }
        }
    }

    fun installApk(file: File) {
        viewModelScope.launch {
            if (Shell.rootAccess()) {
                SuUtils.installApk(file)
            } else {
                // TODO Add Ability to install Apk
            }
        }
    }
}