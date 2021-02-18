package com.jaqxues.sniptools.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.theme.DarkTheme

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
        Image(
            painterResource(id = R.drawable.sniptools_logo),
            "App Logo",
            Modifier.preferredSize(50.dp)
        )

        Providers(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                message,
                Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun EmptyScreenPreview() {
    DarkTheme {
        Surface {
            EmptyScreenMessage(message = "Previewing Empty Screen")
        }
    }
}