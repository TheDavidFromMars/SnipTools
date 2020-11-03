package com.jaqxues.sniptools.packimpl.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.AUTO_SAVE_SNAPS
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 11.10.20 - Time 12:13.
 */
@Composable
fun SavingScreen() {
    Column(Modifier.padding(16.dp)) {
        SwitchPreference(AUTO_SAVE_SNAPS) {
            TitleAndDescription(
                title = "Auto Save Snaps",
                description = "Save every incoming Snap automatically"
            )
        }
    }
}
