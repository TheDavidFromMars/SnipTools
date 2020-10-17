package com.jaqxues.sniptools.packimpl.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.jaqxues.akrolyb.prefs.Preference
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.akrolyb.prefs.putPref


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 08.10.20 - Time 21:19.
 */
@Composable
private fun CustomCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), content = content)
}

@Composable
private fun SplitCard(
    modifier: Modifier = Modifier,
    left: @Composable () -> Unit,
    right: @Composable () -> Unit
) {
    CustomCard(modifier) {
        Row(Modifier.padding(16.dp)) {
            left()
            Spacer(Modifier.weight(1f))
            right()
        }
    }
}

@Composable
fun CheckboxCard(toggled: Boolean, onToggle: (Boolean) -> Unit, text: @Composable () -> Unit) {
    SplitCard(
        Modifier.toggleable(toggled, onValueChange = onToggle),
        left = text,
        right = { Checkbox(checked = toggled, onCheckedChange = onToggle) }
    )
}

@Composable
fun SwitchCard(toggled: Boolean, onToggle: (Boolean) -> Unit, text: @Composable () -> Unit) {
    SplitCard(
        Modifier.toggleable(toggled, onValueChange = onToggle),
        left = text,
        right = { Switch(checked = toggled, onCheckedChange = onToggle) }
    )
}

@Composable
fun SwitchPreference(pref: Preference<Boolean>, text: @Composable () -> Unit) {
    var toggled by remember {
        mutableStateOf(pref.getPref())
    }

    fun onToggle(active: Boolean) {
        toggled = active
        pref.putPref(active)
    }
    SwitchCard(toggled = toggled, onToggle = ::onToggle, text)
}

@Composable
fun <T> DropdownPreference(
    pref: Preference<T>,
    values: Map<T, String>,
    title: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var current by remember { mutableStateOf(pref.getPref()) }

    DropdownMenu(toggle = {
        Card(
            Modifier.fillMaxWidth()
                .toggleable(value = expanded, onValueChange = { expanded = !expanded }),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                title()
                Spacer(Modifier.weight(1f))
                Text(values[current] ?: "Unknown")
                Image(
                    imageResource(id = android.R.drawable.arrow_down_float),
                    Modifier.padding(horizontal = 8.dp).size(8.dp)
                )
            }
        }
    }, expanded = expanded, onDismissRequest = { expanded = false }) {
        values.forEach { (k, v) ->
            DropdownMenuItem(onClick = {
                pref.putPref(k)
                current = k
                expanded = false
            }) {
                Text(v)
            }
        }
    }
}