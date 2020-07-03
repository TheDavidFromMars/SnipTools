package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.amitshekhar.DebugDB
import com.amitshekhar.debug.sqlite.DebugDBFactory
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.utils.ContextContainer
import com.jaqxues.sniptools.utils.after
import de.robv.android.xposed.XposedHelpers


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:17.
 */
class ForcedHooks : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {


        XposedHelpers.findAndHookMethod("com.snap.media.provider.MediaPackageFileProvider", classLoader, "onCreate", after {
            if (BuildConfig.DEBUG) {
                DebugDB.initialize(
                    ContextContainer.getModuleContextNotNull(context),
                    context,
                    DebugDBFactory()
                )
            }
        })
    }
}