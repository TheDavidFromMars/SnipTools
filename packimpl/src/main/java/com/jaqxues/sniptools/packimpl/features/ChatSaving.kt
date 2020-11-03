package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.sniptools.pack.ExternalDestination
import com.jaqxues.sniptools.pack.IFeature


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 02.07.20 - Time 19:42.
 */
class ChatSaving : IFeature() {
    override fun getDestinations() = emptyArray<ExternalDestination>()

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
    }
}