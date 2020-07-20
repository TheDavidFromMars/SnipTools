package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.FORCE_APP_DECK
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.FORCE_SC_APP_DECK_MODE
import com.jaqxues.sniptools.utils.before


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:19.
 */
class MiscFeatures : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {

        /*
        Disables or activates the new App Deck in Snapchat.
        */
        val appDeckMode = FORCE_SC_APP_DECK_MODE.getPref()
        if (appDeckMode >= 0)
        hookMethod(FORCE_APP_DECK, before {
                if (it.args[0].toString() == "NV_GROWTH_MODE")
                    it.result = appDeckMode
            })
    }
}