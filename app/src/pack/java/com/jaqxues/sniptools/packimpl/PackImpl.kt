package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.sniptools.data.LocalPackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.ModPack


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:13.
 */
class PackImpl @Suppress("unused") constructor(metadata: LocalPackMetadata) : ModPack(metadata) {

    override fun getStaticFragments() = emptyList<BaseFragment>()

    override fun loadFeatureManager() = FeatureManager(FeatureSet)
}
