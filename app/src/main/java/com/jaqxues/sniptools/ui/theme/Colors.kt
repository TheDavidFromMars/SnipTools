package com.jaqxues.sniptools.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 31.08.20 - Time 17:41.
 */

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    val colors = darkColors(
        primary = Color(0xFFF1CC02),
        primaryVariant = Color(0xFFD8B402)
    )
    MaterialTheme(colors = colors) {
        content()
    }
}