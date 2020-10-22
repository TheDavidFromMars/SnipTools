package com.jaqxues.sniptools.data

import com.jaqxues.akrolyb.prefs.Preference
import com.jaqxues.akrolyb.prefs.Types.Companion.genericType


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 25.06.20 - Time 20:33.
 */
object Preferences {
    val SELECTED_PACKS = Preference("selected_packs", emptySet<String>())
}