package com.jaqxues.sniptools.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaqxues.akrolyb.prefs.edit
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.utils.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.db.PackDao
import com.jaqxues.sniptools.db.ServerPackEntity
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
class PackRepository(
    private val packLoadManager: PackLoadManager,
    private val retrofit: GitHubApiService,
    private val packDao: PackDao,
    private val preferences: SharedPreferences
) {
    private val packDirectory = File(PathProvider.modulesPath)
    private val loadablePackStates = mutableMapOf<String, MutableLiveData<StatefulPackData>>()

    // Private Mutable LiveData
    private val _localPacks = MutableLiveData<List<String>>(emptyList())
    private val _lastChecked = MutableLiveData(preferences.getLong("packs_last_refresh", -1))

    // Exposed public LiveData
    val localPacks: LiveData<List<String>> = _localPacks
    val lastChecked: LiveData<Long> = _lastChecked
    val serverPacks = packDao.getAllPacks()

    val packLoadChanges = packLoadManager.packLoadChanges

    @WorkerThread
    suspend fun refreshLocalPacks(
        context: Context,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        val jarFileList =
            packDirectory.listFiles { file -> file.isFile && file.extension == "jar" }

        if (jarFileList.isNullOrEmpty()) {
            _localPacks.postValue(emptyList())
            return
        }
        refreshPackStates(jarFileList, context, certificate, packBuilder)
        _localPacks.postValue(jarFileList.map(File::getName))
    }

    private suspend fun refreshPackStates(
        files: Array<File>,
        context: Context,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        for (packFile in files) {
            try {
                packLoadManager.ensureInitialState(context, packFile, certificate, packBuilder)
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
            packLoadManager.requestLoadPack(context, packFile, certificate, packBuilder)
        } catch (t: Throwable) {
            Timber.e(t, "Could not activate Pack")
        }
    }

    suspend fun deactivatePack(
        context: Context,
        packFile: File,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        SELECTED_PACKS.edit { it - packFile.name }
        packLoadManager.requestUnloadPack(context, packFile, certificate, packBuilder)
    }

    fun getStateFor(packFileName: String): LiveData<StatefulPackData> {
        return loadablePackStates.getOrPut(packFileName) {
            MutableLiveData(packLoadManager.getStateFor(packFileName))
        }
    }

    suspend fun deletePack(packFile: File) {
        _localPacks.postValue(_localPacks.value!! - packFile.name)
        SELECTED_PACKS.edit { it - packFile.name }
        packFile.delete()
        packLoadManager.deletePackState(packFile.name)
        loadablePackStates.remove(packFile.name)
    }

    suspend fun loadActivatedPacks(
        context: Context,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        for (packName in SELECTED_PACKS.getPref()) {
            try {
                packLoadManager.requestLoadPack(
                    context, File(PathProvider.modulesPath, packName), certificate, packBuilder
                )
            } catch (t: Throwable) {
                Timber.e(t, "Failed to load Pack")
            }
        }
    }

    suspend fun collectPackChanges() {
        packLoadChanges.collect { nameState ->
            val (packFileName, state) = nameState
            if (packFileName in loadablePackStates)
                loadablePackStates.getValue(packFileName).postValue(state)
        }
    }

    suspend fun refreshServerPacks() {
        try {
            packDao.updateAll(*retrofit.getServerPacks().map {
                ServerPackEntity(
                    it.scVersion, it.name, it.devPack,
                    it.packVersion, it.packVersionCode, it.minApkVersionCode, 0
                )
            }.toTypedArray())

            System.currentTimeMillis().let { time ->
                preferences.edit { putLong("packs_last_refresh", time) }
                _lastChecked.postValue(time)
            }
        } catch (t: Throwable) {
            Timber.e(t, "Could not refresh Packs")
        }
    }

    suspend fun downloadPack(packName: String) {
        retrofit.getPackFile(packName).byteStream().use { input ->
            File(packDirectory, "$packName.jar").outputStream().use(input::copyTo)
        }
    }
}