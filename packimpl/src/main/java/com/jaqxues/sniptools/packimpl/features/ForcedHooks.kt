package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.sniptools.fragments.PackFragment
import com.jaqxues.sniptools.pack.IFeature


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:17.
 */
class ForcedHooks : IFeature() {
    override fun getFragments() = emptyArray<PackFragment>()

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
    }
}