package com.jaqxues.sniptools.packimpl.utils

import com.jaqxues.akrolyb.prefs.Preference


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 14.06.20 - Time 20:28.
 */
object PackPreferences {
    val ASK_SCREENSHOT_CONFIRMATION = Preference(
        "ask_screenshot_confirmation", true, Boolean::class
    )
}