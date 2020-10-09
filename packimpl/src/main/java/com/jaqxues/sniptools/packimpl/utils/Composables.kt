package com.jaqxues.sniptools.packimpl.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.akrolyb.prefs.Preference
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.akrolyb.prefs.putPref


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 08.10.20 - Time 21:19.
 */

@Composable
fun SwitchPreference(pref: Preference<Boolean>, text: @Composable () -> Unit) {
    var toggled by remember {
        mutableStateOf(pref.getPref())
    }
    fun onToggle(active: Boolean) {
        toggled = active
        pref.putPref(active)
    }
    Card(
        Modifier.fillMaxWidth().toggleable(toggled, onValueChange = ::onToggle),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(Modifier.padding(16.dp)) {
            text()
            Spacer(Modifier.weight(1f))
            Switch(checked = toggled, onCheckedChange = ::onToggle)
        }
    }
}