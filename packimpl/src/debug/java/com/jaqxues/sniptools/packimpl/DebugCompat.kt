package com.jaqxues.sniptools.packimpl

import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.utils.IDebugCompat
import kotlin.reflect.KClass


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.07.20 - Time 17:08.
 */
object DebugCompat : IDebugCompat {
    override val debugFeature: KClass<out IFeature>? get() = DebugFeature::class
    override val debugPrefsClass: KClass<*>? get() = DebugPreferences::class
    override val debugHookDecs: Any? get() = DebugMemberDeclarations
}
