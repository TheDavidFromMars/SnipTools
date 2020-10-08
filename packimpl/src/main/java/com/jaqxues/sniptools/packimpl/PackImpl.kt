package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.prefs.PrefManager
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.fragments.PackFragment
import com.jaqxues.sniptools.pack.ModPack
import com.jaqxues.sniptools.packimpl.utils.FeatureSet
import com.jaqxues.sniptools.packimpl.utils.PackPreferences
import timber.log.Timber
import com.jaqxues.sniptools.BuildConfig as AppBuildConfig


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:13.
 */
@Suppress("unused")
class PackImpl constructor(metadata: PackMetadata) : ModPack(metadata) {
    init {
        Timber.d("Pack Constructor was Invoked!")

        // Assert that this Pack is running with a compatible BuildType combination
        performAdditionalChecks(metadata)

        // Loading Pack Preferences (with Debug Preferences if they exist)
        Timber.d("Loading Pack Preferences")
        PrefManager.addPreferences(
            *arrayOf(
                PackPreferences::class,
                DebugCompat.debugPrefsClass
            ).filterNotNull().toTypedArray()
        )
        Timber.d("Fully Initialized Pack")
    }

    override val featureManager by lazy { FeatureManager(FeatureSet) }

    override val staticFragments = emptyList<PackFragment>()
    override val lateInitActivity = "com.snap.mushroom.MainActivity"

    private fun performAdditionalChecks(metadata: PackMetadata) {
        check(
            BuildConfig.DEBUG == metadata.devPack
                    && BuildConfig.DEBUG == AppBuildConfig.DEBUG
        ) { "Incompatible DevPack - Debug Combination" }
    }
}
