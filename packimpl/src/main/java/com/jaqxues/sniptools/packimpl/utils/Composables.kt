package com.jaqxues.sniptools.packimpl.utils

import android.R
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
import androidx.compose.ui.unit.sp
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
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { left() }
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

    SwitchCard(toggled = toggled, onToggle = {
        toggled = it
        pref.putPref(it)
    }, text = text)
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
        SplitCard(
            Modifier.toggleable(value = expanded, onValueChange = { expanded = !expanded }),
            left = title,
            right = {
                Text(values[current] ?: "Unknown")
                Image(
                    imageResource(id = R.drawable.arrow_down_float),
                    Modifier.padding(horizontal = 8.dp).size(8.dp)
                )
            }
        )
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

@Composable
fun TitleAndDescription(title: String, description: String) {
    Column {
        Text(title, Modifier.padding(bottom = 2.dp))
        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            Text(description, fontSize = 12.sp)
        }
    }
}