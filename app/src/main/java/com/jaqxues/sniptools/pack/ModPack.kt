package com.jaqxues.sniptools.pack

import android.content.Context
import com.jaqxues.akrolyb.genhook.FeatureHelper
import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.pack.AppData
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.pack.PackFactoryBase
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.CustomApplication
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.utils.buildMetadata
import com.jaqxues.sniptools.utils.installedScVersion
import java.io.File
import java.util.jar.Attributes


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
abstract class ModPack(localPackMetadata: PackMetadata) : ModPackBase<PackMetadata>(localPackMetadata) {
    abstract fun getStaticFragments(): List<BaseFragment>

    abstract fun loadFeatureManager(): FeatureManager<out IFeature>
}

abstract class IFeature: FeatureHelper() {
    abstract fun getFragments(): Array<BaseFragment>
}

open class PackFactory: PackFactoryBase<PackMetadata>() {
    override val appData = AppData(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, CustomApplication.PACKAGE_NAME, BuildConfig.FLAVOR)

    override fun buildMeta(attributes: Attributes, context: Context, file: File) =
        attributes.buildMetadata(file)
}

class SafePackFactory: PackFactory() {
    override fun performChecks(packMetadata: PackMetadata, context: Context, file: File) {
        super.performChecks(packMetadata, context, file)

        val scVersion = context.installedScVersion
        val supportedScVersion = packMetadata.scVersion
        if (scVersion != supportedScVersion)
            throw UnsupportedScVersion(scVersion, supportedScVersion)
    }
}

class UnsupportedScVersion(scVersion: String?, supportedScVersion: String):
    Exception("Current Snapchat Version not supported ('$scVersion' - '$supportedScVersion')")