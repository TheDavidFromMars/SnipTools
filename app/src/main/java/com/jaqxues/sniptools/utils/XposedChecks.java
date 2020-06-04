package com.jaqxues.sniptools.utils;

import androidx.annotation.Keep;

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 11:01.
 *
 * Class in Java since Kotlin classes are lazy loaded, which makes Hooking harder
 */
@Keep
public class XposedChecks {
    @Keep
    public static int getXposedVersion() {
        return -1;
    }

    public static boolean isModuleActive() {
        return getXposedVersion() != -1;
    }

    public static void applySelfHooks(ClassLoader classLoader) {
        // Not importing Xposed Classes to allow having Xposed Code in the class and run code without crashing from a
        // non-Xposed Context
        de.robv.android.xposed.XposedHelpers.findAndHookMethod(
                "com.jaqxues.sniptools.utils.XposedChecks",
                classLoader,
                "getXposedVersion",
                de.robv.android.xposed.XC_MethodReplacement.returnConstant(de.robv.android.xposed.XposedBridge.getXposedVersion())
        );
    }
}

