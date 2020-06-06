package com.jaqxues.sniptools

import android.app.Application
import android.content.Context
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.utils.Security
import com.jaqxues.sniptools.pack.ModPack
import com.jaqxues.sniptools.pack.SafePackFactory
import com.jaqxues.sniptools.utils.XposedChecks
import com.jaqxues.sniptools.utils.after
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage
import timber.log.Timber
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 10:53.
 */
class HookManager : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val packageName = lpparam.packageName

        if (packageName == CustomApplication.PACKAGE_NAME) {
            XposedChecks.applySelfHooks(lpparam.classLoader)
            return
        }

        if (packageName != "com.snapchat.android") return
        if (hasHooked.getAndSet(true)) {
            Timber.d("Hooks already injected")
            return
        }

        Timber.d("Loading Preferences")

        findAndHookMethod(
            "android.app.Application",
            lpparam.classLoader,
            "attach",
            Context::class.java,
            after { param ->
                Timber.d("Invoked Application#attach(Context), preparing Hook Stage")

                val snapContext = param.args[0] as Context
                val snapApp = param.thisObject as Application

                val pack: ModPack = ModPackBase.buildPack(
                    snapContext,
                    File(""),
                    Security.certificateFromApk(snapContext, CustomApplication.PACKAGE_NAME),
                    SafePackFactory()
                )
            })
    }

    companion object {
        val hasHooked = AtomicBoolean()
    }
}