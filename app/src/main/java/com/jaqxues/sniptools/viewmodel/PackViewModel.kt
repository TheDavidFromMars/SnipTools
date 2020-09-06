package com.jaqxues.sniptools.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.data.StatefulPackData
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.repository.PackRepository
import kotlinx.coroutines.launch
import java.io.File
import java.security.cert.X509Certificate
import kotlin.random.Random


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:41.
 */
class PackViewModel(private val packRepo: PackRepository) : ViewModel() {

    val lastChecked: LiveData<Long> =
        MutableLiveData(System.currentTimeMillis() - Random.nextLong(0, 1e8.toLong()))

    val localPacks: LiveData<List<String>> = packRepo.localPacks

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

    fun deletePack(packFile: File) = packRepo.deletePack(packFile)
}