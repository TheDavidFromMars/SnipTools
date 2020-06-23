package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.prefs.PrefManager
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.ModPack


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:13.
 */
@Suppress("unused")
class PackImpl constructor(metadata: PackMetadata) : ModPack(metadata) {
    init {
        PrefManager.addPreferences(PackPreferences::class)
    }

    override val featureManager by lazy { FeatureManager(FeatureSet) }

    override val staticFragments = emptyList<BaseFragment>()
}
