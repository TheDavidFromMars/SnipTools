package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.FeatureProvider
import com.jaqxues.sniptools.pack.IFeature
import kotlin.reflect.KClass


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
    override val forcedFeatures = arrayOf(
        "forced" to ForcedHooks::class,
        DebugFeature.debugFeature?.let { "debug" to it }
    ).filterNotNull().toMap()

    override val hookDefs = emptyArray<Any>()
}

// Allow DebugFeature to be included only for debug builds: 2 different implementations
interface CheckDebugFeature {
    val debugFeature: KClass<out IFeature>?
}
