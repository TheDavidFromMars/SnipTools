package com.jaqxues.sniptools.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.repository.PackRepository
import kotlinx.coroutines.launch
import java.io.File
import java.security.cert.X509Certificate


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:41.
 */
class PackViewModel(private val packRepo: PackRepository) : ViewModel() {
    val localPacks: LiveData<List<String>> = packRepo.localPacks
    val packLoadChanges = packRepo.packLoadChanges

    fun getStateDataForPack(packFileName: String): LiveData<StatefulPackData> =
        packRepo.getStateFor(packFileName)

    fun refreshLocalPacks(context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        viewModelScope.launch {
            packRepo.refreshLocalPacks(context, certificate, packBuilder)
        }
    }

    fun activatePack(context: Context, packFile: File, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        viewModelScope.launch {
            packRepo.activatePack(context, packFile, certificate, packBuilder)
        }
    }

    fun deactivatePack(context: Context, packFile: File, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        viewModelScope.launch {
            packRepo.deactivatePack(context, packFile, certificate, packBuilder)
        }
    }

    fun deletePack(packFile: File) {
        viewModelScope.launch {
            packRepo.deletePack(packFile)
        }
    }

    fun loadActivatedPacks(context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        viewModelScope.launch { packRepo.collectPackChanges() }
        viewModelScope.launch { packRepo.loadActivatedPacks(context, certificate, packBuilder) }
    }
}