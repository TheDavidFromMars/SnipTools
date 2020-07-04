package com.jaqxues.sniptools.utils

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 11:03.
 */
inline fun before(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
    object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam) {
            hook(param)
        }
    }

inline fun after(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                hook(param)
            }
        }

inline fun replaceReturn(crossinline hook: (XC_MethodHook.MethodHookParam) -> Any?) =
    object : XC_MethodReplacement() {
        override fun replaceHookedMethod(param: MethodHookParam): Any? {
            return hook(param)
        }
    }

inline fun replace(crossinline hook: (XC_MethodHook.MethodHookParam) -> Unit) =
    object : XC_MethodReplacement() {
        override fun replaceHookedMethod(param: MethodHookParam): Any? {
            hook(param)
            return null
        }
    }
