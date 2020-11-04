package com.jaqxues.sniptools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.akrolyb.genhook.states.StateLogger
import com.jaqxues.akrolyb.pack.ModPackBase
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.akrolyb.utils.Security
import com.jaqxues.akrolyb.utils.XposedChecks
import com.jaqxues.sniptools.pack.ModPack
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.utils.CommonSetup
import com.jaqxues.sniptools.utils.ContextContainer
import com.jaqxues.sniptools.utils.PathProvider
import com.jaqxues.sniptools.utils.Preferences.SELECTED_PACKS
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
        CommonSetup.initTimber()
        if (hasHooked.getAndSet(true)) {
            Timber.d("Hooks already injected")
            return
        }

        Timber.d("Loading Preferences")
        CommonSetup.initPrefs()

        val unhookContainer = arrayOfNulls<XC_MethodHook.Unhook>(2)
        unhookContainer[0] = findAndHookMethod(
            "android.app.Application",
            lpparam.classLoader,
            "attach",
            Context::class.java,
            after { param ->
                try {
                    Timber.d("Invoked Application#attach(Context), preparing Hook Stage")

                    val snapContext = param.args[0] as Context
                    val snapApp = param.thisObject as Application
                    val moduleContext = ContextContainer.createModuleContext(snapApp)
                    ContextContainer.setModuleContext(moduleContext)

                    for (selectedPack in SELECTED_PACKS.getPref()) {
                        val pack: ModPack = ModPackBase.buildPack(
                            snapContext,
                            File(PathProvider.modulesPath, selectedPack),
                            if (BuildConfig.DEBUG) null else Security.certificateFromApk(
                                moduleContext,
                                CustomApplication.PACKAGE_NAME
                            ),
                            packBuilder = PackFactory(true)
                        )
                        val featureManager = pack.featureManager
                        featureManager.stateDispatcher.apply {
                            addStateListener(StateLogger())
                        }

                        unhookContainer[1] = findAndHookMethod(
                            pack.lateInitActivity,
                            lpparam.classLoader,
                            "onCreate",
                            Bundle::class.java,
                            after { param ->
                                ContextContainer.setActivity(param.thisObject as Activity)
                                Timber.d("Invoked LandingPageActivity#onCreate(Bundle), invoking lateInit")

                                featureManager.lateInitAll(
                                    lpparam.classLoader,
                                    param.thisObject as Activity
                                )

                                unhookContainer[1]!!.unhook()
                            })

                        featureManager.loadAll(lpparam.classLoader, snapContext)
                        unhookContainer[0]!!.unhook()
                    }
                } catch (t: Throwable) {
                    val message = ModPack.getPackErrorMessage(t)
                    ContextContainer.getModuleContext()?.let { ctx ->
                        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show()
                    }
                    Timber.e(t, message)
                }
            })
    }

    companion object {
        val hasHooked = AtomicBoolean()
    }
}