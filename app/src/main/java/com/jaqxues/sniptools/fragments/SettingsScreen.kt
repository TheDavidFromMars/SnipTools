package com.jaqxues.sniptools.fragments

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.jaqxues.sniptools.utils.PrefEntries
import com.jaqxues.sniptools.utils.PrefEntry
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.getBoolean
import com.jaqxues.sniptools.viewmodel.SettingsViewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.11.20 - Time 12:37.
 */
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    ScrollableColumn(Modifier.padding(16.dp)) {
        val prefs = AmbientContext.current.getSharedPreferences("main", Context.MODE_PRIVATE)

        Category(title = "Updates") {
            UpdateSettings(settingsViewModel)
        }

        Category(title = "Root Options") {
            RootSettings(prefs)
        }
    }

    HandleDownloadEvent(settingsViewModel)
}

@Composable
private fun UpdateSettings(settingsViewModel: SettingsViewModel) {
    val ctx = AmbientContext.current

    PrefButton(onClick = { settingsViewModel.downloadApk(ctx) }) {
        Text("Download Latest Apk")
    }
}

@Composable
private fun RootSettings(prefs: SharedPreferences) {
    PrefSwitch(prefs, PrefEntries.shouldKillSc) {
        Text("Kill Snapchat after Preference Changes")
    }
}

@Composable
fun Category(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(
        title,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
    Divider(color = MaterialTheme.colors.primary, modifier = Modifier.padding(top = 8.dp))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        content()
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
    }
}

@Composable
private fun PrefButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    TextButton(
        onClick = onClick,
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        border = ButtonDefaults.outlinedBorder
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) { content() }
    }
}

@Composable
fun PrefSwitch(
    prefs: SharedPreferences,
    pref: PrefEntry<Boolean>,
    content: @Composable () -> Unit
) {
    var enabled by remember {
        mutableStateOf(prefs.getBoolean(pref))
    }
    val setNew: (Boolean) -> Unit = {
        enabled = it
        prefs.edit { putBoolean(pref.key, it) }
    }
    Row(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .toggleable(value = enabled, onValueChange = setNew)
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Box(Modifier.weight(1f)) {
            content()
        }
        Switch(checked = enabled, onCheckedChange = setNew)
    }
}


@Composable
fun HandleDownloadEvent(settingsViewModel: SettingsViewModel) {
    val evtState = settingsViewModel.downloadEvents.collectAsState(null)
    when (val evt = evtState.value) {
        null,
        is Request.Loading -> {
        }
        is Request.Success -> {
            if (evt.data == null) {
                Toast.makeText(
                    AmbientContext.current,
                    "Already on latest Apk",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                settingsViewModel.installApk(evt.data)
            }
        }
        is Request.Error -> {
            Toast.makeText(
                AmbientContext.current,
                "Could not download apk (${evt.t.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}