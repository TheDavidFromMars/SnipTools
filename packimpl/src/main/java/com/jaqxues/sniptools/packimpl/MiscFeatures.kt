package com.jaqxues.sniptools.packimpl

import android.content.Context
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.utils.PackPreferences
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers


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
        Disables or activates the new App Deck in Snapchat. Possible values: DISABLED | THREE | FIVE
        */
        XposedHelpers.findAndHookMethod(
            "c65", classLoader, "i", XC_MethodReplacement.returnConstant(
                XposedHelpers.findClass("l65", classLoader)
                    .getDeclaredField(PackPreferences.FORCE_SC_APP_DECK_MODE.getPref()).get(null)
            )
        )
    }
}