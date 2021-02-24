package com.jaqxues.sniptools.ui.screens

import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.db.ServerPackEntity
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.Request
import com.jaqxues.sniptools.ui.viewmodel.ServerPackViewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 06.11.20 - Time 21:52.
 */
@Composable
fun PackHistoryScreen(
    navController: NavController,
    scVersion: String,
    serverPackViewModel: ServerPackViewModel
) {
    remember { serverPackViewModel.refreshPackHistory(scVersion) }
        .observeAsState().value?.let { request ->
            when (request) {
                is Request.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        "Could not refresh Pack History", Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                }
            }
        }
    val packHistory = serverPackViewModel.getPackHistory(scVersion).observeAsState().value

    if (packHistory.isNullOrEmpty()) {
        EmptyScreenMessage("Pack History not available")
    } else {
        PackHistoryContent(packHistory) { packName ->
            serverPackViewModel.downloadPack(packName)
        }
        HandlePackDownloadEvents(serverPackViewModel.downloadEvents, navController)
    }
}

@Composable
fun PackHistoryContent(packHistory: List<ServerPackEntity>, onDownload: (String) -> Unit) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(packHistory) { pack ->
            ListCardElement {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(pack.name, Modifier.weight(1f).padding(end = 16.dp))
                        IconButton(onClick = {
                            onDownload(pack.name)
                        }) {
                            Icon(
                                painterResource(R.drawable.ic_baseline_cloud_download_48),
                                "Pack Download",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Divider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colors.primary)

                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        val dateTxt = if (pack.createdAt == null) "No date available" else
                            DateFormat.getLongDateFormat(LocalContext.current)
                                .format(pack.createdAt)

                        when {
                            pack.minApkVersionCode == BuildConfig.VERSION_CODE ->
                                Text("Compatible with your Apk", fontSize = 14.sp)
                            pack.minApkVersionCode <= BuildConfig.VERSION_CODE ->
                                Text("Probably compatible with your Apk", fontSize = 14.sp)
                            else -> Text(
                                "Likely incompatible with your Apk", fontSize = 14.sp,
                                color = MaterialTheme.colors.error
                            )
                        }

                        Text(
                            "Released on: $dateTxt",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            "Pack Version: ${pack.packVersion} (Version Code: ${pack.packVersionCode})",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}