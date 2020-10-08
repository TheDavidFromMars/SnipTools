package com.jaqxues.sniptools.pack

import android.content.Context
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.data.Preferences.SELECTED_PACKS
import com.jaqxues.sniptools.data.StatefulPackData
import com.jaqxues.sniptools.utils.PathProvider
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber
import java.io.File
import java.security.cert.X509Certificate
import java.util.concurrent.ConcurrentHashMap


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
object PackLoadManager {
    private val packLoadStates = ConcurrentHashMap<String, StatefulPackData>()
    private val channel = BroadcastChannel<Pair<String, StatefulPackData>>(Channel.CONFLATED)
    val packLoadChanges = channel.asFlow()

    private suspend inline fun putState(packFileName: String, getState: () -> StatefulPackData) {
        val state = getState()
        packLoadStates[packFileName] = state
        channel.send(packFileName to state)
    }

    suspend fun loadState(
        context: Context,
        packFile: File,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ) {
        if (!packLoadStates.containsKey(packFile.name)) {
            getInitialState(context, packFile, certificate, packBuilder)
        }
    }

    suspend fun getInitialState(
        context: Context,
        packFile: File,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory = PackFactory(false)
    ): PackMetadata {
        try {
            val metadata = getMetadata(context, packFile, certificate, packBuilder)
            putState(packFile.name) { StatefulPackData.AvailablePack(packFile, metadata) }
            return metadata
        } catch (t: Throwable) {
            putState(packFile.name) { StatefulPackData.CorruptedPack(packFile, t.message!!) }
            throw t
        }
    }

    suspend fun loadActivatedPacks(context: Context, certificate: X509Certificate? = null, packBuilder: PackFactory) {
        SELECTED_PACKS.getPref().forEach {
            try {
                requestLoadPack(
                    context,
                    File(PathProvider.modulesPath, it),
                    certificate,
                    packBuilder
                )
            } catch (t: Throwable) {
                Timber.e(t, "Failed to load Pack")
            }
        }
    }

    suspend fun requestLoadPack(
        context: Context,
        packFile: File,
        certificate: X509Certificate? = null,
        packBuilder: PackFactory
    ): ModPack {
        val metadata = try {
            getMetadata(context, packFile, certificate, packBuilder)
        } catch (t: Throwable) {
            putState(packFile.name) { StatefulPackData.CorruptedPack(packFile, t.message!!) }
            throw t
        }

        try {
            val pack: ModPack =
                ModPackBase.buildPack(context, packFile, certificate, packBuilder, metadata)
            putState(packFile.name) { StatefulPackData.LoadedPack(packFile, metadata, pack) }
            return pack
        } catch (t: Throwable) {
            putState(packFile.name) {
                StatefulPackData.PackLoadError(
                    packFile,
                    metadata,
                    t.message!!
                )
            }
            throw t
        }
    }

    suspend fun requestUnloadPack(
        context: Context,
        packFile: File,
        certificate: X509Certificate?,
        packBuilder: PackFactory
    ) {
        getInitialState(context, packFile, certificate, packBuilder)
    }

    fun deletePackState(packFileName: String) = packLoadStates.remove(packFileName)

    fun getStateFor(packName: String) = packLoadStates.getValue(packName)

    private fun getMetadata(
        context: Context,
        packFile: File,
        certificate: X509Certificate?,
        packBuilder: PackFactory
    ) = ModPackBase.extractMetadata(context, packFile, certificate, packBuilder)
}