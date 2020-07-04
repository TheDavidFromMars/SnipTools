package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.SCREENSHOT_DETECTED
import de.robv.android.xposed.XC_MethodReplacement


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

        // Bypass Screenshot detection
        hookMethod(SCREENSHOT_DETECTED, XC_MethodReplacement.DO_NOTHING)
    }
}