package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.akrolyb.genhook.decs.before
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.CAPTION_EDIT_TEXT_VIEW
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.FORCE_APP_DECK
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.FORCE_SC_APP_DECK_MODE
import de.robv.android.xposed.XposedBridge.hookAllConstructors
import de.robv.android.xposed.XposedHelpers.findClass


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
                    if (it.args[0].toString() in setOf("NGS_GROWTH_MODE", "NGS_MODE"))
                        it.result = appDeckMode
                })

        hookAllConstructors(findClass(CAPTION_EDIT_TEXT_VIEW.className, classLoader), after { param ->
            (param.thisObject as EditText).apply {
                filters = filters.filter { it !is InputFilter.LengthFilter }.toTypedArray()
            }
        })
    }
}