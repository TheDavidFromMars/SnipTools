package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:19.
 */
class MiscFeatures : IFeature() {
    override fun getFragment(): BaseFragment? {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
        TODO("Not yet implemented")
    }
}