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

    val STORY_STEALTH_ENABLED = Preference(
        "story_stealth_enabled", false, Boolean::class
    )

    val FORCE_SC_APP_DECK_MODE = Preference(
        "force_sc_app_deck_mode", -1, Int::class
    )
}