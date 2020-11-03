package com.jaqxues.sniptools.packimpl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 08.10.20 - Time 21:19.
 */
@Composable
fun DebugScreen() {
    Column(Modifier.padding(16.dp)) {
        SwitchPreference(DebugPreferences.DB_DEBUG_SERVER) {
            TitleAndDescription(
                title = "Use Database Server",
                description = "Activates a Database Server running on localhost:8888"
            )
        }
    }
}
