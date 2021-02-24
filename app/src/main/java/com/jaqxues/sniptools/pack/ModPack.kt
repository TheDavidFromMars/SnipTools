package com.jaqxues.sniptools.pack

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.jaqxues.akrolyb.genhook.FeatureHelper
import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.pack.*
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.ui.NavScreen
import com.jaqxues.sniptools.utils.buildMetadata
import com.jaqxues.sniptools.utils.installedScVersion
import java.io.File
import java.util.jar.Attributes


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
abstract class ModPack(metadata: PackMetadata) : ModPackBase<PackMetadata>(metadata) {
    abstract val disabledFeatures: LiveData<Set<String>>
    abstract val featureManager: FeatureManager<out IFeature>
    abstract val staticFragments: Array<ExternalDestination>
    abstract val lateInitActivity: String

    companion object {
        fun getPackErrorMessage(t: Throwable): String = when (t) {
            is PackNotFoundException -> "Activated Pack could not be found"
            is PackEnvironmentException -> {
                val cause = t.cause
                if (cause is UnsupportedScVersion) {
                    cause.message
                } else t.message
            }
            is PackAttributesException,
            is PackMetadataException -> "Pack data could not be extracted"
            is PackClassLoaderException,
            is PackReflectionException -> "Pack could not be loaded"
            else -> "Unknown Error (${t.message})"
        } ?: "Error without any message"
    }
}

abstract class IFeature: FeatureHelper() {
    abstract fun getDestinations(): Array<ExternalDestination>
}

data class ExternalDestination(
    override val route: String,
    val defaultName: String,
    val screenComposable: @Composable () -> Unit
): NavScreen {

    @get:Composable
    override val screenName: String
        get() = defaultName

    @get:Composable
    override val icon: DestinationIcon?
        get() = null
}

class PackFactory(private val checkScVersion: Boolean): PackFactoryBase<PackMetadata>() {
    override val appData = AppData(BuildConfig.VERSION_CODE, BuildConfig.DEBUG)

    override fun buildMeta(attributes: Attributes, context: Context, file: File) =
        attributes.buildMetadata(file)

    override fun performChecks(packMetadata: PackMetadata, context: Context, file: File) {
        super.performChecks(packMetadata, context, file)

        if (checkScVersion) {
            val scVersion = context.installedScVersion
            val supportedScVersion = packMetadata.scVersion
            if (scVersion != supportedScVersion)
                throw UnsupportedScVersion(scVersion, supportedScVersion)
        }
    }
}

class UnsupportedScVersion(scVersion: String?, supportedScVersion: String):
    Exception("Current Snapchat Version not supported ('$scVersion' - '$supportedScVersion')")

data class PackMetadata(
    val flavour: String,
    val scVersion: String,
    val name: String,

    override val devPack: Boolean,
    override val packVersion: String,
    override val packVersionCode: Int,
    override val packImplClass: String,
    override val minApkVersionCode: Int,
    override val isEncrypted: Boolean
): IPackMetadata
