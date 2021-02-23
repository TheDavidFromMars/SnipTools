package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import android.text.InputFilter
import android.widget.EditText
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.fragment.Destinations
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.CAPTION_EDIT_TEXT_VIEW
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.DISABLE_CAPTION_LENGTH_LIMIT
import de.robv.android.xposed.XposedBridge.hookAllConstructors
import de.robv.android.xposed.XposedHelpers.findClass


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 00:19.
 */
class MiscFeatures : IFeature() {
    override fun getDestinations() = arrayOf(Destinations.MISC.destination)

    override fun loadFeature(classLoader: ClassLoader, context: Context) {

        if (DISABLE_CAPTION_LENGTH_LIMIT.getPref())
            hookAllConstructors(findClass(CAPTION_EDIT_TEXT_VIEW.className, classLoader), after { param ->
                (param.thisObject as EditText).apply {
                    filters = filters.filter { it !is InputFilter.LengthFilter }.toTypedArray()
                }
            })
    }
}