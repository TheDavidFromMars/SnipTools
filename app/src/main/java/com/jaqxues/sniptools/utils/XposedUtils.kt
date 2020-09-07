package com.jaqxues.sniptools.utils

import com.jaqxues.akrolyb.genhook.decs.HookWrapper
import com.jaqxues.akrolyb.genhook.decs.ReplaceWrapper
import de.robv.android.xposed.XC_MethodHook


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 11:03.
 */
inline fun before(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
    object : HookWrapper() {
        override fun before(param: MethodHookParam) {
            hook(param)
        }
    }

inline fun after(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
    object : HookWrapper() {
        override fun after(param: MethodHookParam) {
            hook(param)
        }
    }

inline fun replaceReturn(crossinline hook: (XC_MethodHook.MethodHookParam) -> Any?) =
    object : ReplaceWrapper() {
        override fun replace(param: MethodHookParam): Any? {
            return hook(param)
        }
    }

inline fun replace(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
    object : ReplaceWrapper() {
        override fun replace(param: MethodHookParam): Any? {
            hook(param)
            return null
        }
    }
