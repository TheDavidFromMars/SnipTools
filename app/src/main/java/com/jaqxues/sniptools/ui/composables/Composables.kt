package com.jaqxues.sniptools.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.R

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 23.06.20 - Time 19:43.
 */
@Composable
fun EmptyScreenMessage(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(imageResource(id = R.drawable.sniptools_logo), Modifier.preferredSize(50.dp))

        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            Text(
                message,
                Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}