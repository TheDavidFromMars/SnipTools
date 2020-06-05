package com.jaqxues.sniptools.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.data.ServerPackMetadata
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.utils.PackUtils
import com.jaqxues.sniptools.utils.PathProvider
import com.jaqxues.sniptools.utils.Request
import timber.log.Timber
import java.io.File


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 13.05.20 - Time 10:01.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:42.
 */
class PackRepository(private val retrofit: GitHubApiService) {
    // Private Mutable LiveData
    private val _localMetadata = MutableLiveData<List<PackMetadata>>()
    private val _remoteMetadata = MutableLiveData<Request<List<ServerPackMetadata>>>()

    // Exposed public LiveData
    val localMetadata: LiveData<List<PackMetadata>> = _localMetadata
    val remoteMetadata: LiveData<Request<List<ServerPackMetadata>>> = _remoteMetadata

    private val packDirectory = File(PathProvider.modulesPath)

    @WorkerThread
    fun refreshLocalMetadata() {
        val jarFileList = packDirectory.listFiles { pathName -> pathName.isFile && pathName.extension == "jar" }

        if (jarFileList.isNullOrEmpty()) {
            _localMetadata.postValue(emptyList())
            return
        }

        val packs = jarFileList.associate { file ->
            val metadata = try {
                PackUtils.getPackMetadata(file)
            } catch (t: Throwable) {
                Timber.e(t, "Could not load Metadata from Pack Attributes")
                throw t
            }
            metadata.name to metadata
        }
    }
}