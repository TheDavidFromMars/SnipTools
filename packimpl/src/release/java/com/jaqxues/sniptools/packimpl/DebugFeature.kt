package com.jaqxues.sniptools.packimpl

import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.utils.IDebugCompat
import kotlin.reflect.KClass


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.07.20 - Time 23:03.
 */
object DebugCompat : IDebugCompat {
    override val debugFeature: KClass<out IFeature>? = null
    override val debugPrefsClass: KClass<*>? get() = null
    override val debugHookDecs: Any? get() = null
}