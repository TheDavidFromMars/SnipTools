package com.jaqxues.sniptools.data

import java.io.File

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.09.20 - Time 19:42.
 *
 * Classes representing different LoadStates of a Pack, including both successful or failed Load
 * States
 */
sealed class StatefulPackData {
    abstract val packFile: File
    abstract val packMetadata: PackMetadata

    /**
     * PackMetadata could not be extracted successfully -> JarFile corrupt
     */
    data class CorruptedPack(override val packFile: File, val message: String) : StatefulPackData() {
        override val packMetadata get() = error("Corrupted Pack does not contain Metadata")
    }

    /**
     * Pack is available and ready to load
     */
    data class AvailablePack(override val packFile: File, override val packMetadata: PackMetadata) :
        StatefulPackData()

    /**
     * Attempt to load Pack failed
     */
    data class PackLoadError(
        override val packFile: File,
        override val packMetadata: PackMetadata,
        val message: String
    ) : StatefulPackData()

    /**
     * Pack was loaded successfully
     */
    data class LoadedPack(override val packFile: File, override val packMetadata: PackMetadata) :
        StatefulPackData()

    val isActive: Boolean get() = this is LoadedPack || this is PackLoadError
}