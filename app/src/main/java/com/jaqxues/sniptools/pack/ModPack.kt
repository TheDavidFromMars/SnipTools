package com.jaqxues.sniptools.pack

import android.content.Context
import com.jaqxues.akrolyb.genhook.FeatureHelper
import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.pack.AppData
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.pack.PackFactoryBase
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.CustomApplication
import com.jaqxues.sniptools.data.ActivePackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.utils.installedScVersion
import java.io.File
import java.util.jar.Attributes


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
abstract class ModPack(localPackMetadata: ActivePackMetadata) : ModPackBase<ActivePackMetadata>(localPackMetadata) {
    abstract fun getStaticFragments(): List<BaseFragment>

    abstract fun loadFeatureManager(): FeatureManager<out IFeature>
}

abstract class IFeature: FeatureHelper() {
    abstract fun getFragment(): BaseFragment?
}

open class PackFactory: PackFactoryBase<ActivePackMetadata>() {
    override val appData = AppData(BuildConfig.VERSION_CODE, BuildConfig.DEBUG, CustomApplication.PACKAGE_NAME, BuildConfig.FLAVOR)

    override fun buildMeta(attributes: Attributes, context: Context, file: File): ActivePackMetadata {
        fun getValue(name: String) =
            attributes.getValue(name) ?: throw IllegalStateException("Pack did not include \"$name\" Attribute")

        return ActivePackMetadata(
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

class SafePackFactory: PackFactory() {
    override fun performChecks(packMetadata: ActivePackMetadata, context: Context, file: File) {
        super.performChecks(packMetadata, context, file)

        if (packMetadata.scVersion != context.installedScVersion)
            throw IllegalStateException("Current Snapchat Version not supported")
    }
}
