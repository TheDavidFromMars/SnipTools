package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations
import com.jaqxues.sniptools.utils.before


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 09.07.20 - Time 15:35.
 */
class UnlimitedViewing : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
        hookConstructor(MemberDeclarations.SNAP_MODEL_CONSTRUCTOR, before {
            it.args[8] = true
        })
    }
}