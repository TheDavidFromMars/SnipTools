package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.SCREENSHOT_DETECTED
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.ASK_SCREENSHOT_CONFIRMATION
import com.jaqxues.sniptools.packimpl.utils.tryCreateDialog
import com.jaqxues.sniptools.utils.invokeOriginalMethod
import com.jaqxues.sniptools.utils.replace
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.07.20 - Time 10:25.
 */
class ScreenshotBypass : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {

        val screenshotHook = if (ASK_SCREENSHOT_CONFIRMATION.getPref()) {
            val dialogShown = AtomicBoolean()
            replace { param ->
                if (dialogShown.getAndSet(true))
                    return@replace

                GlobalScope.launch(Dispatchers.Main) {
                    tryCreateDialog {
                        setTitle("Send Screenshot Notification")
                        setMessage("Do you want to dispatch the Screenshot Notification and let the other user know you made a screenshot?")
                        setCancelable(false)
                        setOnDismissListener {
                            dialogShown.set(false)
                        }

                        setPositiveButton("Yes") { dialog, _ ->
                            // Cannot be called from Main Thread
                            GlobalScope.launch(Dispatchers.Default) {
                                param.invokeOriginalMethod()
                                XposedBridge.invokeOriginalMethod(
                                    param.method, param.thisObject, param.args
                                )
                            }
                            dialog.dismiss()
                        }

                        setNeutralButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }

                    }?.show()
                }
            }
        } else
            XC_MethodReplacement.DO_NOTHING

        hookMethod(SCREENSHOT_DETECTED, screenshotHook)
    }
}