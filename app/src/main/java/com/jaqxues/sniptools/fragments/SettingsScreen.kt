package com.jaqxues.sniptools.fragments

import android.widget.Toast
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonConstants
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.viewmodel.SettingsViewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.11.20 - Time 12:37.
 */
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val ctx = ContextAmbient.current


        TextButton(
            onClick = {
                settingsViewModel.downloadApk(ctx)
            },
            border = ButtonConstants.defaultOutlinedBorder
        ) {
            Text("Download Latest Apk", modifier = Modifier.padding(8.dp))
        }
    }
    HandleDownloadEvent(settingsViewModel)
}


@Composable
fun HandleDownloadEvent(settingsViewModel: SettingsViewModel) {
    val evtState = settingsViewModel.downloadEvents.collectAsState(null)
    when (val evt = evtState.value) {
        null,
        is Request.Loading -> {}
        is Request.Success -> {
            if (evt.data == null) {
                Toast.makeText(
                    ContextAmbient.current,
                    "Already on latest Apk",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                settingsViewModel.installApk(evt.data)
            }
        }
        is Request.Error -> {
            Toast.makeText(
                ContextAmbient.current,
                "Could not download apk (${evt.t.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}