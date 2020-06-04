package com.jaqxues.sniptools.utils

import de.robv.android.xposed.XC_MethodHook


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
