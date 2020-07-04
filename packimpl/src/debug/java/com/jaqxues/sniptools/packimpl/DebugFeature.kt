package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.amitshekhar.DebugDB
import com.amitshekhar.debug.sqlite.DebugDBFactory
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.utils.CheckDebugFeature
import com.jaqxues.sniptools.utils.ContextContainer
import com.jaqxues.sniptools.utils.after
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import kotlin.reflect.KClass


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.07.20 - Time 23:03.
 */
class DebugFeature: IFeature() {

    override fun getFragments(): Array<BaseFragment> = emptyArray()

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
        findAndHookMethod("com.snap.media.provider.MediaPackageFileProvider", classLoader, "onCreate", after {
            if (BuildConfig.DEBUG) {
                DebugDB.initialize(
                    ContextContainer.getModuleContextNotNull(context),
                    context,
                    DebugDBFactory()
                )
            }
        })
    }

    companion object: CheckDebugFeature {
        override val debugFeature: KClass<out IFeature>? get() = DebugFeature::class
    }
}