package com.jaqxues.sniptools.pack

import android.content.Context
import com.jaqxues.akrolyb.genhook.FeatureHelper
import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.pack.AppData
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.pack.PackFactoryBase
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.data.LocalPackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.utils.installedScVersion
import java.io.File
import java.util.jar.Attributes


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
abstract class IModPack(localPackMetadata: LocalPackMetadata) : ModPackBase<LocalPackMetadata>(localPackMetadata) {
    abstract fun getStaticFragments(): List<BaseFragment>

    abstract fun loadFeatureManager(): FeatureManager<out IFeature>
}

abstract class IFeature: FeatureHelper() {
    abstract fun getFragment(): BaseFragment?
}

open class PackFactory: PackFactoryBase<LocalPackMetadata>() {
    override val appData = AppData(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, BuildConfig.APPLICATION_ID, BuildConfig.FLAVOR)

    override fun buildMeta(attributes: Attributes, context: Context, file: File): LocalPackMetadata {
        fun getValue(name: String) =
            attributes.getValue(name) ?: throw IllegalStateException("Pack did not include \"$name\" Attribute")

        return LocalPackMetadata(
            isTutorial = false,
            latest = true,
            flavour = getValue("Flavour"),
            scVersion = getValue("ScVersion"),
            name = file.name.dropLast(4),
            devPack = getValue("Development").equals("true", false),
            packVersion = getValue("PackVersion"),
            packVersionCode = getValue("PackVersionCode").toInt(),
            packImplClass = getValue("PackImpl"),
            minApkVersionCode = getValue("MinApkVersionCode").toInt()
        )
    }
}

class SafePackFactory(): PackFactory() {
    override fun performChecks(packMetadata: LocalPackMetadata, context: Context, file: File) {
        super.performChecks(packMetadata, context, file)

        if (packMetadata.scVersion != context.installedScVersion)
            throw IllegalStateException("Current Snapchat Version not supported")
    }
}
