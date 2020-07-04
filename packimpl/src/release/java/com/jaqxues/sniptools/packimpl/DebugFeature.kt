package com.jaqxues.sniptools.packimpl

import com.jaqxues.sniptools.pack.IFeature
import kotlin.reflect.KClass


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.07.20 - Time 23:03.
 */
object DebugFeature: CheckDebugFeature {
    override val debugFeature: KClass<out IFeature>? = null
}