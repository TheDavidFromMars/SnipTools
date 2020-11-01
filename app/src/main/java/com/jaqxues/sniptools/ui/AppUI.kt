package com.jaqxues.sniptools.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaqxues.sniptools.ui.theme.DarkTheme


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 31.08.20 - Time 14:32.
 */
@Composable
fun AppScreen(screen: @Composable () -> Unit) {
    DarkTheme {
        Surface(color = MaterialTheme.colors.background) {
            screen()
        }
    }
}
