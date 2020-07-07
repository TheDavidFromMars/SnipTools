package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.MARK_STORY_AS_VIEWED
import de.robv.android.xposed.XC_MethodReplacement


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 07.07.20 - Time 20:19.
 */
class StealthViewing : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {

        // Stories (Non-Friends and Friends)
        hookMethod(MARK_STORY_AS_VIEWED, XC_MethodReplacement.DO_NOTHING)
    }
}