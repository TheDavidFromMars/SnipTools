package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.FeatureProvider
import com.jaqxues.sniptools.pack.IFeature


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:22.
 */
object FeatureSet: FeatureProvider<IFeature> {
    override val disabledFeatures = emptyList<String>()

    override val optionalFeatures = mapOf(
        "misc" to MiscFeatures::class,
        "chat" to ChatSaving::class
    )
    override val forcedFeatures = mapOf(
        "forced" to ForcedHooks::class
    )
    override val hookDefs = emptyArray<Any>()
}