package com.jaqxues.sniptools.fragments

import androidx.compose.animation.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.pack.PackFactory
import com.jaqxues.sniptools.pack.PackMetadata
import com.jaqxues.sniptools.pack.StatefulPackData
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.viewModel
import com.jaqxues.sniptools.viewmodel.PackViewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 06.09.20 - Time 19:30.
 */

@Composable
fun PackSelectorTab() {
    val packViewModel by viewModel<PackViewModel>()
    ContextAmbient.current.let { ctx ->
        onActive { packViewModel.refreshLocalPacks(ctx, null, PackFactory(false)) }
    }
    val _localPacks by packViewModel.localPacks.observeAsState()
    val localPacks = _localPacks

    if (localPacks.isNullOrEmpty()) {
        EmptyScreenMessage("No Packs found")
    } else {
        LazyColumnFor(localPacks) {
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

                    LocalActionRow(packData, onChangelog = {

                    }, onChangeActive = {
                        if (it) {
                            packViewModel.activatePack(
                                context,
                                packData.packFile,
                                null,
                                PackFactory(false)
                            )
                        } else {
                            packViewModel.deactivatePack(
                                context,
                                packData.packFile,
                                null,
                                PackFactory(false)
                            )
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
fun LocalActionRow(
    packData: StatefulPackData,
    modifier: Modifier = Modifier,
    onChangelog: () -> Unit,
    onChangeActive: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onChangelog) {
            Image(
                vectorResource(id = R.drawable.ic_baseline_history_48),
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.preferredHeight(24.dp)
            )
        }

        Switch(
            packData.isActive,
            onCheckedChange = onChangeActive,
            color = if (packData !is StatefulPackData.PackLoadError) Color(0xFF00AA00) else Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp).size(50.dp)
        )

        IconButton(onClick = onDelete) {
            Image(
                vectorResource(id = R.drawable.ic_baseline_remove_circle_outline_48),
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.preferredHeight(24.dp)
            )
        }
    }
}

@Composable
fun PackMetadataLayout(metadata: PackMetadata) {
    ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
        Text("Pack Type: ${if (metadata.devPack) "Developer" else "User"}", fontSize = 12.sp)
        Text("Snapchat Version: ${metadata.scVersion}", fontSize = 12.sp)
        Text("Pack Version: ${metadata.packVersion}", fontSize = 12.sp)
    }
}
