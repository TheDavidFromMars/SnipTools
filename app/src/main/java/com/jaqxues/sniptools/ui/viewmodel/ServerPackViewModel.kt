package com.jaqxues.sniptools.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.repository.PackRepository
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.liveDataRequest
import com.jaqxues.sniptools.utils.sendAsRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 24.10.20 - Time 15:56.
 */
@HiltViewModel
class ServerPackViewModel @Inject constructor(private val packRepo: PackRepository) : ViewModel() {
    val lastChecked = packRepo.lastChecked
    val serverPacks = packRepo.serverPacks
    private val _downloadEvents = Channel<Request<String>>()
    val downloadEvents = _downloadEvents.receiveAsFlow()

    fun refreshServerPacks() {
        viewModelScope.launch(Dispatchers.IO) {
            packRepo.refreshServerPacks()
        }
    }

    fun downloadPack(packName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _downloadEvents sendAsRequest {
                packRepo.downloadPack(packName)
            }
        }
    }

    fun getPackHistory(scVersion: String) = packRepo.getPackHistory(scVersion)

    fun refreshPackHistory(scVersion: String) = liveDataRequest(Dispatchers.IO) {
        packRepo.refreshPackHistory(scVersion)
    }
}