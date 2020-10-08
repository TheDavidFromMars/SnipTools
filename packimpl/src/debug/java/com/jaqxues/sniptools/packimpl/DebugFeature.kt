package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.amitshekhar.DebugDB
import com.amitshekhar.debug.sqlite.DebugDBFactory
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.DebugMemberDeclarations.ON_CREATE_PROVIDER
import com.jaqxues.sniptools.packimpl.DebugPreferences.DB_DEBUG_SERVER
import com.jaqxues.sniptools.utils.ContextContainer


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.07.20 - Time 23:03.
 */
class DebugFeature : IFeature() {

    override fun getFragments() = arrayOf(DebugFragment())

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
        if (DB_DEBUG_SERVER.getPref()) {

            hookMethod(ON_CREATE_PROVIDER, after {
                DebugDB.initialize(
                    ContextContainer.getModuleContextNotNull(context),
                    context,
                    DebugDBFactory()
                )
            })
        }
    }
}