package com.jaqxues.sniptools.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaqxues.akrolyb.prefs.edit
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.data.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.data.StatefulPackData
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.pack.PackLoadManager
import com.jaqxues.sniptools.utils.PathProvider
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.io.File
import java.security.cert.X509Certificate


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 13.05.20 - Time 10:01.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:42.
 */
class PackRepository(private val retrofit: GitHubApiService) {
    private val packDirectory = File(PathProvider.modulesPath)
    private val loadablePackStates = mutableMapOf<String, MutableLiveData<StatefulPackData>>()

    // Private Mutable LiveData
    private val _localPacks = MutableLiveData<List<String>>(emptyList())

    // Exposed public LiveData
    val localPacks: LiveData<List<String>> = _localPacks

    val packLoadChanges = PackLoadManager.packLoadChanges

    @WorkerThread
    suspend fun refreshLocalPacks(context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        val jarFileList =
            packDirectory.listFiles { file -> file.isFile && file.extension == "jar" }

        if (jarFileList.isNullOrEmpty()) {
            _localPacks.postValue(emptyList())
            return
        }
        refreshPackStates(jarFileList, context, certificate, packBuilder)
        _localPacks.postValue(jarFileList.map(File::getName))
    }

    private suspend fun refreshPackStates(files: Array<File>, context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        for (packFile in files) {
            try {
                PackLoadManager.ensureInitialState(context, packFile, certificate, packBuilder)
            } catch (t: Throwable) {
                Timber.e(t, "Could not load PackState correctly")
            }
        }
    }

    suspend fun activatePack(
        context: Context,
        packFile: File,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        SELECTED_PACKS.edit { it + packFile.name }
        try {
            PackLoadManager.requestLoadPack(context, packFile, certificate, packBuilder)
        } catch (t: Throwable) {
            Timber.e(t,"Could not activate Pack")
        }
    }

    suspend fun deactivatePack(context: Context, packFile: File, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        SELECTED_PACKS.edit { it - packFile.name }
        PackLoadManager.requestUnloadPack(context, packFile, certificate, packBuilder)
    }

    fun getStateFor(packFileName: String): LiveData<StatefulPackData> {
        return loadablePackStates.getOrPut(packFileName) {
            MutableLiveData(PackLoadManager.getStateFor(packFileName))
        }
    }

    fun deletePack(packFile: File) {
        SELECTED_PACKS.edit { it - packFile.name }
        packFile.delete()
        PackLoadManager.deletePackState(packFile.name)
        loadablePackStates.remove(packFile.name)
        _localPacks.postValue(_localPacks.value!!.let {
            val list = it.toMutableList()
            list.remove(packFile.name)
            list
        })
    }

    suspend fun loadActivatedPacks(context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        PackLoadManager.packLoadChanges.collect(::onPackStateChanged)

        for (packName in SELECTED_PACKS.getPref()) {
            try {
                PackLoadManager.requestLoadPack(
                    context, File(PathProvider.modulesPath, packName), certificate, packBuilder
                )
            } catch (t: Throwable) {
                Timber.e(t, "Failed to load Pack")
            }
        }
    }

    private fun onPackStateChanged(nameState: Pair<String, StatefulPackData>) {
        val (packFileName, state) = nameState
        if (packFileName in loadablePackStates)
            loadablePackStates.getValue(packFileName).postValue(state)
    }
}