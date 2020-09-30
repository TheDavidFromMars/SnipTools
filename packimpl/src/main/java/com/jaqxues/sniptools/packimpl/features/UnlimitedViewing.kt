package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.akrolyb.genhook.decs.before
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_IMAGE_UNLIMITED
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_VIDEO_LOOPING


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

        if (SNAP_VIDEO_LOOPING.getPref() || SNAP_IMAGE_UNLIMITED.getPref())
            hookConstructor(MemberDeclarations.SNAP_MODEL_CONSTRUCTOR, before {
                val snapType = it.args[6].toString()

                if ((snapType == "IMAGE" && SNAP_IMAGE_UNLIMITED.getPref())
                    || (snapType == "VIDEO" && SNAP_VIDEO_LOOPING.getPref())) {
                    it.args[8] = true
                }
            })
    }
}