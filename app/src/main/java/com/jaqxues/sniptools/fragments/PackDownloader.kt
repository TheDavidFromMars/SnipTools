package com.jaqxues.sniptools.fragments

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.db.ServerPackEntity
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.formatRelativeAbbrev
import com.jaqxues.sniptools.utils.viewModel
import com.jaqxues.sniptools.viewmodel.ServerPackViewModel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 23.10.20 - Time 19:01.
 */
@Composable
fun PackDownloaderTab() {
    val packViewModel by viewModel<ServerPackViewModel>()
    onActive { packViewModel.refreshServerPacks() }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ServerPackContent(packViewModel)

        val time = packViewModel.lastChecked.observeAsState().value ?: -1
        FooterText(
            annotatedString {
                append("Last Checked: ")
                highlight {
                    append(time.formatRelativeAbbrev)
                }
            }
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun RemoteActionRow(pack: ServerPackEntity, onDownload: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onDownload) {
            Icon(
                vectorResource(R.drawable.ic_baseline_cloud_download_48),
                modifier = Modifier.preferredHeight(50.dp).padding(8.dp)
            )
        }
    }
}

@Composable
fun ColumnScope.ServerPackContent(packViewModel: ServerPackViewModel) {
    val serverPacks = packViewModel.serverPacks.observeAsState().value

    if (serverPacks.isNullOrEmpty()) {
        EmptyScreenMessage("No Packs available", Modifier.weight(1f))
    } else {
        LazyColumnFor(items = serverPacks, Modifier.weight(1f)) { pack ->
            ExpandablePackLayout(packName = pack.name) {
                RemoteActionRow(pack) {
                    packViewModel.downloadPack(pack.name)
                }
            }
        }
    }
}