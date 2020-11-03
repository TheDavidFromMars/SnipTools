package com.jaqxues.sniptools.packimpl.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_IMAGE_UNLIMITED
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_VIDEO_LOOPING
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 10.10.20 - Time 15:15.
 */
@Composable
fun UnlimitedScreen() {
    Column(Modifier.padding(16.dp)) {
        SwitchPreference(SNAP_VIDEO_LOOPING) {
            TitleAndDescription(
                title = "Loop Videos",
                description = "Repeat all videos"
            )
        }
        Spacer(Modifier.padding(8.dp))
        SwitchPreference(SNAP_IMAGE_UNLIMITED) {
            TitleAndDescription(
                title = "Disable Snap Timer",
                description = "View all Snaps for an indefinite amount of time"
            )
        }
    }
}
