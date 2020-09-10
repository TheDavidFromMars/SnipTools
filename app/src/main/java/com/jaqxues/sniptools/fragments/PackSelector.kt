package com.jaqxues.sniptools.fragments

import androidx.compose.animation.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.data.PackMetadata
import com.jaqxues.sniptools.data.StatefulPackData
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.viewmodel.PackViewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 06.09.20 - Time 19:30.
 */

@Composable
fun PackSelectorTab(packViewModel: PackViewModel) {
    packViewModel.refreshLocalPacks(ContextAmbient.current, null, PackFactory(false))
    val localPacks by packViewModel.localPacks.observeAsState()

    ScrollableColumn {
        localPacks!!.forEach {
            val packData by packViewModel.getStateDataForPack(it).run {
                observeAsState(value!!)
            }
            PackElementLayout(packData, packViewModel)
        }
    }
}

@Composable
fun PackElementLayout(packData: StatefulPackData, packViewModel: PackViewModel) {
    when (packData) {
        is StatefulPackData.CorruptedPack -> ExpandablePackLayout(
            packName = packData.packFile.name,
            color = Color.Red
        ) {
            Text(
                packData.message,
                Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontSize = 12.sp
            )
        }
        else -> {
            val color = animate(
                when (packData) {
                    is StatefulPackData.AvailablePack ->
                        Color.White
                    is StatefulPackData.LoadedPack ->
                        Color(0xFF00AA00)
                    is StatefulPackData.PackLoadError ->
                        Color.Red
                    else -> error("Illegal PackLoad State")
                }
            )

            ExpandablePackLayout(packName = packData.packMetadata.name, color = color) {
                Column(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                    Divider(Modifier.padding(horizontal = 20.dp))


                    val context = ContextAmbient.current

                    var invoked by remember { mutableStateOf(false) }
                    IconButtonRow(packData, onChangelog = {

                    }, onChangeActive = {
                        if (!invoked) {
                            invoked = true
                            return@IconButtonRow
                        }
                        if (it) {
                            packViewModel.activatePack(
                                context,
                                packData.packFile,
                                null,
                                PackFactory(false)
                            )
                        } else {
                            if (packData.isActive) {
                                packViewModel.deactivatePack(
                                    context,
                                    packData.packFile,
                                    null,
                                    PackFactory(false)
                                )
                            }
                        }
                    }, onDelete = {
                        packViewModel.deletePack(packData.packFile)
                    })
                    Divider(Modifier.padding(horizontal = 80.dp))

                    Spacer(Modifier.padding(8.dp))
                    PackMetadataLayout(metadata = packData.packMetadata)

                    if (packData is StatefulPackData.PackLoadError) {
                        Text(packData.message, Modifier.padding(vertical = 8.dp), fontSize = 12.sp)
                    } else {
                        Spacer(Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun IconButtonRow(
    packData: StatefulPackData,
    modifier: Modifier = Modifier,
    onChangelog: () -> Unit,
    onChangeActive: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier.gravity(Alignment.CenterHorizontally),
        verticalGravity = Alignment.CenterVertically
    ) {
        IconButton(onClick = onChangelog) {
            Image(
                vectorResource(id = R.drawable.ic_baseline_history_48),
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.preferredHeight(40.dp).padding(8.dp)
            )
        }

        Switch(
            packData.isActive,
            onCheckedChange = onChangeActive,
            color = if (packData !is StatefulPackData.PackLoadError) Color(0xFF00AA00) else Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(onClick = onDelete) {
            Image(
                vectorResource(id = R.drawable.ic_baseline_remove_circle_outline_48),
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.preferredHeight(40.dp).padding(8.dp)
            )
        }
    }
}

@Composable
fun PackMetadataLayout(metadata: PackMetadata) {
    Text(
        "Pack Type: ${if (metadata.devPack) "Developer" else "User"}",
        fontSize = 12.sp,
        color = Color.LightGray
    )
    Text("Snapchat Version: ${metadata.scVersion}", fontSize = 12.sp, color = Color.LightGray)
    Text("Pack Version: ${metadata.packVersion}", fontSize = 12.sp, color = Color.LightGray)
}
