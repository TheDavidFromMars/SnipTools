package com.jaqxues.sniptools.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

/*
@Preview
@Composable
fun AppScreen() {
    DarkTheme {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            if (drawerState.isClosed)
                                drawerState.open()
                            else
                                drawerState.close()
                        }) {
                            Icon(vectorResource(R.drawable.ic_menu_camera))
                        }
                    },
                    title = {
                        Column {
                            Text("SnapTools")
                        }
                    }
                )
            },
        ) {
            ModalDrawerLayout(
                drawerContent = {
                    Column(
                        Modifier
                            .composed {
                                drawWithContent {
                                    drawRect(
                                        brush = LinearGradient(
                                            colors = arrayOf(
                                                0xFF1A237E,
                                                0xFF283593,
                                                0xFF0277BD
                                            ).map(::Color),
                                            startX = 0f,
                                            startY = 0f,
                                            endX = size.width,
                                            endY = size.height
                                        )
                                    )
                                    drawContent()
                                }
                            }.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(Modifier.padding(16.dp))
                        Image(imageResource(id = R.drawable.sniptools_logo))
                        Text("SnipTools")
                        Text("Are you happy now", fontSize = 12.sp)
                        Spacer(Modifier.padding(16.dp))
                    }
                    arrayOf("Home", "Pack Management", "Settings", "About Us").forEach {
                        Text(
                            it,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                .clickable(onClick = {})
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = MaterialTheme.colors.primary.copy(alpha = 0.3f))
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                    }
                },
                drawerState = drawerState
            ) {
                HomeScreen()
            }
        }
    }
}
*/
