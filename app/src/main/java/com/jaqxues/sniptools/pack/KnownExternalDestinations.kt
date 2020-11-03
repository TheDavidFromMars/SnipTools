package com.jaqxues.sniptools.pack

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.CheckIconTypes

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.11.20 - Time 12:22.
 */
enum class KnownExternalDestinations(
    val route: String,
    @StringRes val stringRes: Int,
    private val _icon: Any
) {
    GENERAL("general", R.string.nav_ext_general, R.drawable.ic_baseline_tune_48),
    MISC("misc", R.string.nav_ext_misc, Icons.Default.Edit),
    SAVING("saving", R.string.nav_ext_saving, R.drawable.ic_baseline_save_alt_48),
    DEBUG("debug_tools", R.string.nav_ext_debug, R.drawable.ic_baseline_bug_report_48),
    UNLIMITED("unlimited", R.string.nav_ext_unlimited, R.drawable.ic_baseline_timer_off_48),
    STEALTH("stealth", R.string.nav_ext_stealth, R.drawable.ic_baseline_visibility_off_48),
    SCREENSHOT("screenshot", R.string.nav_ext_screenshot, R.drawable.ic_baseline_no_photography_48);

    init {
        CheckIconTypes.checkType(_icon)
    }

    @Composable
    val icon: VectorAsset
        get() = CheckIconTypes.getFor(_icon)
}
