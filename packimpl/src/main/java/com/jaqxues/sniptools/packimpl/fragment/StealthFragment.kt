package com.jaqxues.sniptools.packimpl.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.STORY_STEALTH_ENABLED
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 11.10.20 - Time 12:20.
 */
@Composable
fun StealthScreen() {
    Column(Modifier.padding(16.dp)) {
        SwitchPreference(STORY_STEALTH_ENABLED) {
            TitleAndDescription(
                title = "Enable Story Stealth",
                description = "Viewing Stories of Friends without them noticing"
            )
        }
    }
}
