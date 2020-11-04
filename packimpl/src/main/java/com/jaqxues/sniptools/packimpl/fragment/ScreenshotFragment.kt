package com.jaqxues.sniptools.packimpl.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.ASK_SCREENSHOT_CONFIRMATION
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 11.10.20 - Time 12:16.
 */
@Composable
fun ScreenshotScreen() {
    Column(Modifier.padding(16.dp)) {
        SwitchPreference(ASK_SCREENSHOT_CONFIRMATION) {
            TitleAndDescription(
                title = "Confirm Screenshot Notification",
                description = "Allows you to choose whether to send the screenshot notification"
            )
        }
    }
}
