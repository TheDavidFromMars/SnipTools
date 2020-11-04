package com.jaqxues.sniptools.fragments

import android.widget.Toast
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.LocalScreen
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.utils.formatRelativeAbbrev
import com.jaqxues.sniptools.viewmodel.ServerPackViewModel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 23.10.20 - Time 19:01.
 */
@Composable
fun PackDownloaderTab(navController: NavController, packViewModel: ServerPackViewModel) {
    onActive { packViewModel.refreshServerPacks() }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ServerPackContent(packViewModel, Modifier.weight(1f))
        PackDownloaderFooter(packViewModel)
    }

    val downloadEvents = packViewModel.downloadEvents.collectAsState(null)
    downloadEvents.value?.let { evt ->
        when (evt) {
            is Request.Loading -> {
            }
            is Request.Error -> {
                Toast.makeText(
                    ContextAmbient.current,
                    "Could not download Pack (${evt.t.message})",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Request.Success -> {
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
fun ServerPackContent(packViewModel: ServerPackViewModel, modifier: Modifier = Modifier) {
    val serverPacks = packViewModel.serverPacks.observeAsState().value

    if (serverPacks.isNullOrEmpty()) {
        EmptyScreenMessage("No Packs available", modifier)
    } else {
        LazyColumnFor(items = serverPacks, modifier) { pack ->
            ExpandablePackLayout(packName = pack.name) {
                Column(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                    Divider(Modifier.padding(horizontal = 20.dp))

                    RemoteActionRow(
                        onDownload = {
                            packViewModel.downloadPack(pack.name)
                        }, onShowHistory = {

                        }, onShowChangeLog = {

                        })

                    Divider(Modifier.padding(horizontal = 80.dp))

                    Spacer(Modifier.padding(8.dp))
                    ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
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

@Composable
fun PackDownloaderFooter(packViewModel: ServerPackViewModel) {
    val time = packViewModel.lastChecked.observeAsState().value ?: -1
    ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
        Text(annotatedString {
            append("Last Checked: ")
            highlight {
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
                vectorResource(R.drawable.ic_baseline_history_48),
                modifier = Modifier.preferredHeight(24.dp)
            )
        }
        IconButton(onClick = onDownload, Modifier.padding(horizontal = 16.dp)) {
            Icon(
                vectorResource(R.drawable.ic_baseline_cloud_download_48),
                modifier = Modifier.preferredHeight(28.dp)
            )
        }
        IconButton(onClick = onShowChangeLog) {
            Icon(
                vectorResource(R.drawable.ic_baseline_library_books_48),
                modifier = Modifier.preferredHeight(24.dp)
            )
        }
    }
}
