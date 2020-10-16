package com.jaqxues.sniptools.packimpl.utils

import com.jaqxues.akrolyb.prefs.Preference
import com.jaqxues.akrolyb.prefs.Types.Companion.genericType


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 14.06.20 - Time 20:28.
 */
object PackPreferences {
    val ASK_SCREENSHOT_CONFIRMATION = Preference("ask_screenshot_confirmation", true)

    val DISABLED_FEATURES = Preference("disabled_features", emptySet<String>())

    val STORY_STEALTH_ENABLED = Preference("story_stealth_enabled", true)

    val FORCE_SC_APP_DECK_MODE = Preference("force_sc_app_deck_mode", true)

    val SNAP_VIDEO_LOOPING = Preference("snap_video_looping", true)

    val SNAP_IMAGE_UNLIMITED = Preference("snap_image_unlimited", true)

    val DISABLE_CAPTION_LENGTH_LIMIT = Preference("disable_caption_length_limit", true)

    val AUTO_SAVE_SNAPS = Preference("auto_save_snaps", true)
}