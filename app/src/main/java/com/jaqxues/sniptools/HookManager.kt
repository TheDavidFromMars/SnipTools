package com.jaqxues.sniptools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.utils.Security
import com.jaqxues.sniptools.pack.ModPack
import com.jaqxues.sniptools.pack.SafePackFactory
import com.jaqxues.sniptools.utils.ContextContainer
import com.jaqxues.sniptools.utils.XposedChecks
import com.jaqxues.sniptools.utils.after
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
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

        val unhookContainer = arrayOfNulls<XC_MethodHook.Unhook>(2)
        unhookContainer[0] = findAndHookMethod(
            "android.app.Application",
            lpparam.classLoader,
            "attach",
            Context::class.java,
            after { param ->
                Timber.d("Invoked Application#attach(Context), preparing Hook Stage")

                val snapContext = param.args[0] as Context
                val snapApp = param.thisObject as Application
                val moduleContext = ContextContainer.createModuleContext(snapApp)
                ContextContainer.setModuleContext(moduleContext)

                val pack: ModPack = ModPackBase.buildPack(
                    moduleContext,
                    File(""),
                    Security.certificateFromApk(moduleContext, CustomApplication.PACKAGE_NAME),
                    SafePackFactory()
                )
                val featureManager = pack.featureManager

                unhookContainer[1] = findAndHookMethod("com.snapchat.android.LandingPageActivity", lpparam.classLoader, "onCreate", Bundle::class.java, after { param ->
                    Timber.d("Invoked LandingPageActivity#onCreate(Bundle), invoking lateInit")
                    featureManager.lateInitAll(lpparam.classLoader, param.thisObject as Activity)

                    unhookContainer[1]!!.unhook()
                })

                featureManager.loadAll(lpparam.classLoader, snapContext)
                unhookContainer[0]!!.unhook()
            })
    }

    companion object {
        val hasHooked = AtomicBoolean()
    }
}