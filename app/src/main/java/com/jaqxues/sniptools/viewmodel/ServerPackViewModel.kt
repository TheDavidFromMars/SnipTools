package com.jaqxues.sniptools.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.repository.PackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 24.10.20 - Time 15:56.
 */
class ServerPackViewModel @ViewModelInject constructor(private val packRepo: PackRepository) : ViewModel() {
    val lastChecked = packRepo.lastChecked
    val serverPacks = packRepo.serverPacks

    fun refreshServerPacks() {
        viewModelScope.launch(Dispatchers.IO) {
            packRepo.refreshServerPacks()
        }
    }

    fun downloadPack(packName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            packRepo.downloadPack(packName)
        }
    }
}