package com.jaqxues.sniptools.fragments

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.ui.theme.DarkTheme

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 22.11.20 - Time 16:29.
 */
@Composable
fun SupportScreen() {
    val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        // use `item` for separate elements like headers
        // and `items` for lists of identical elements
        item {
            Category("Discord") {
                Text(loremIpsum, textAlign = TextAlign.Center)
            }
            Category("XDA") {
                Text(loremIpsum, textAlign = TextAlign.Center)
            }
            Category("Instagram") {
                Text(loremIpsum, textAlign = TextAlign.Center)
            }
            Category("Twitter") {
                Text(loremIpsum, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun SupportScreenPreview() {
    DarkTheme {
        Surface {
            SupportScreen()
        }
    }
}