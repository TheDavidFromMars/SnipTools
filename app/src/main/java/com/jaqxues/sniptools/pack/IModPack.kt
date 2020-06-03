package com.jaqxues.sniptools.pack

import com.jaqxues.akrolyb.genhook.FeatureHelper
import com.jaqxues.akrolyb.genhook.FeatureManager
import com.jaqxues.akrolyb.pack.ModPack
import com.jaqxues.sniptools.data.LocalPackMetadata
import com.jaqxues.sniptools.fragments.BaseFragment


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 23:58.
 */
abstract class IModPack(localPackMetadata: LocalPackMetadata) : ModPack<LocalPackMetadata>(localPackMetadata) {
    abstract fun getStaticFragments(): List<BaseFragment>

    abstract fun loadFeatureManager(): FeatureManager<out IFeature>
}

abstract class IFeature: FeatureHelper() {
    abstract fun getFragment(): BaseFragment?
}
