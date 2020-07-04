package com.jaqxues.sniptools.packimpl.utils

import com.jaqxues.akrolyb.prefs.Preference
import com.jaqxues.akrolyb.prefs.Types.Companion.genericType


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 14.06.20 - Time 20:28.
 */
object PackPreferences {
    val ASK_SCREENSHOT_CONFIRMATION = Preference(
        "ask_screenshot_confirmation", true, Boolean::class
    )

    val DISABLED_FEATURES = Preference(
        "disabled_features", emptyList<String>(), genericType<List<String>>()
    )
}