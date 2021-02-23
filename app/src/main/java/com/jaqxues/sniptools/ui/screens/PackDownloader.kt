package com.jaqxues.sniptools.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.LocalScreen
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.formatRelativeAbbrev
import com.jaqxues.sniptools.ui.viewmodel.ServerPackViewModel
import kotlinx.coroutines.flow.Flow


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 23.10.20 - Time 19:01.
 */
@Composable
fun PackDownloaderTab(navController: NavController, packViewModel: ServerPackViewModel) {
    LaunchedEffect(Unit) { packViewModel.refreshServerPacks() }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.weight(1f)) {
            ServerPackContent(navController, packViewModel)
        }
        PackDownloaderFooter(packViewModel)
    }

    HandlePackDownloadEvents(packViewModel.downloadEvents, navController)
}

@Composable
fun HandlePackDownloadEvents(downloadFlow: Flow<Request<String>>, navController: NavController) {
    val downloadEvents = downloadFlow.collectAsState(null)
    downloadEvents.value?.let { evt ->
        when (evt) {
            is Request.Loading -> {
            }
            is Request.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    "Could not download Pack (${evt.t.message})",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Request.Success -> {
                navController.popBackStack(navController.graph.startDestination, false)
                navController.navigate(
                    "%s?tab=%s?pack_name=%s"
                        .format(
                            LocalScreen.PackManager.route,
                            PackManagerTabs.PACK_SELECTOR.name,
                            evt.data
                        )
                )
            }
        }
    }
}

@Composable
fun ServerPackContent(navController: NavController, packViewModel: ServerPackViewModel) {
    val serverPacks = packViewModel.serverPacks.observeAsState().value

    if (serverPacks.isNullOrEmpty()) {
        EmptyScreenMessage("No Packs available")
    } else {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(serverPacks) { pack ->
                ExpandablePackLayout(packName = "Pack v${pack.scVersion}") {
                    Column(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                        Divider(Modifier.padding(horizontal = 20.dp))

                        var showChangelog by remember { mutableStateOf(false) }
                        RemoteActionRow(
                            onDownload = {
                                packViewModel.downloadPack(pack.name)
                            }, onShowHistory = {
                                navController.navigate(
                                    "${LocalScreen.PackHistory.route}/${pack.scVersion}"
                                )
                            }, onShowChangeLog = {
                                showChangelog = true
                            })

                        if (showChangelog) {
                            if (pack.changelog == null) {
                                Toast.makeText(
                                    LocalContext.current,
                                    "Pack Changelog not available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                AlertDialog(
                                    modifier = Modifier.fillMaxWidth(),
                                    onDismissRequest = { showChangelog = false },
                                    title = {
                                        Column {
                                            Text(
                                                "Pack Changelog",
                                                style = MaterialTheme.typography.h5
                                            )
                                            Spacer(Modifier.padding(4.dp))
                                            Text(
                                                pack.name,
                                                style = MaterialTheme.typography.subtitle2
                                            )
                                            Divider(
                                                Modifier.padding(top = 8.dp, bottom = 8.dp, end = 32.dp),
                                                color = MaterialTheme.colors.primary
                                            )
                                        }
                                    },
                                    text = {
                                        Text(pack.changelog)
                                    },
                                    buttons = {})
                            }
                        }

                        Divider(Modifier.padding(horizontal = 80.dp))

                        Spacer(Modifier.padding(8.dp))
                        Providers(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                "Pack Type: ${if (pack.devPack) "Developer" else "User"}",
                                fontSize = 12.sp
                            )
                            Text("Snapchat Version: ${pack.scVersion}", fontSize = 12.sp)
                            Text("Pack Version: ${pack.packVersion}", fontSize = 12.sp)
                        }
                        Spacer(Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PackDownloaderFooter(packViewModel: ServerPackViewModel) {
    val time = packViewModel.lastChecked.observeAsState().value ?: -1
    Providers(LocalContentAlpha provides ContentAlpha.medium) {
        Text(buildAnnotatedString {
            append("Last Checked: ")
            Highlight {
                append(time.formatRelativeAbbrev)
            }
        }, fontSize = 12.sp)
    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun RemoteActionRow(
    onShowHistory: () -> Unit,
    onDownload: () -> Unit,
    onShowChangeLog: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onShowHistory) {
            Icon(
                painterResource(R.drawable.ic_baseline_history_48),
                "Pack History",
                modifier = Modifier.preferredHeight(24.dp)
            )
        }
        IconButton(onClick = onDownload, Modifier.padding(horizontal = 16.dp)) {
            Icon(
                painterResource(R.drawable.ic_baseline_cloud_download_48),
                "Pack Download",
                modifier = Modifier.preferredHeight(28.dp)
            )
        }
        IconButton(onClick = onShowChangeLog) {
            Icon(
                painterResource(R.drawable.ic_baseline_library_books_48),
                "Pack Release Notes",
                modifier = Modifier.preferredHeight(24.dp)
            )
        }
    }
}
