package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabConstants.defaultTabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.AppScreen


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 10:56.
 */
class PackManagerFragment : BaseFragment() {
    override val menuId get() = R.id.nav_pack_manager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PackManagerScreen()

            }
        }
    }
}

@Preview
@Composable
fun PackManagerScreen() {
    var currentTab by remember { mutableStateOf(PackManagerTabs.PACK_SELECTOR) }
    AppScreen {
        Column {
            TabRow(
                selectedTabIndex = currentTab.ordinal,
                indicator = {
                    TabConstants.DefaultIndicator(
                        modifier = Modifier.defaultTabIndicatorOffset(
                            it[currentTab.ordinal]
                        ), color = MaterialTheme.colors.primary
                    )
                }
            ) {
                for (tab in PackManagerTabs.values())
                    Tab(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        text = { Text(tab.tabName) })
            }
            Spacer(Modifier.padding(8.dp))

            val packs =
                if (currentTab == PackManagerTabs.PACK_SELECTOR)
                    arrayOf("10.41.6.0", "10.89.7.72", "10.23.62.24")
                else
                    arrayOf("11.0.0.0", "11.23.0.3", "12.32.3.23")
            Crossfade(current = currentTab) {
                Column {
                    packs.forEach {
                        PackElement(it)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewPack() {
    AppScreen {
        PackElement(packName = "11.0.0.72")
    }
}

data class PackData(
    val packName: String,
    val packType: String,
    val scVersion: String,
    val packVersion: String
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PackElement(packName: String) {
    var extendedPack by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = { extendedPack = !extendedPack })
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        val packData = remember {
            PackData(
                packName,
                arrayOf("Premium", "Basic").random(),
                packName,
                packName
            )
        }
        Column {
            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val rotation = animate(if (extendedPack) 0f else 180f)
                Icon(
                    vectorResource(id = R.drawable.ic_pack),
                    Modifier.padding(horizontal = 4.dp)
                        .preferredHeight(8.dp)
                        .drawWithContent {
                            rotate(rotation) {
                                this@drawWithContent.drawContent()
                            }
                        }
                )
                Icon(
                    vectorResource(id = R.drawable.ic_pack),
                    Modifier.padding(horizontal = 16.dp)
                        .preferredHeight(24.dp)
                )
                Text(
                    text = "Pack v$packName"
                )
            }
            AnimatedVisibility(visible = extendedPack) {
                ExtendedPackLayout(packData)
            }
        }
    }
}

@Composable
fun ExtendedPackLayout(packData: PackData) {
    Column(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
        Divider(Modifier.padding(horizontal = 20.dp))
        Row(Modifier.gravity(Alignment.CenterHorizontally)) {
            repeat(3) {
                IconButton(onClick = {}) {
                    Image(
                        vectorResource(id = R.drawable.ic_pack),
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.preferredHeight(40.dp).padding(8.dp)
                    )
                }
            }
        }
        Divider(Modifier.padding(horizontal = 80.dp))
        Spacer(Modifier.padding(8.dp))
        Text("Pack Type: ${packData.packType}", fontSize = 12.sp, color = Color.LightGray)
        Text("Snapchat Version: ${packData.scVersion}", fontSize = 12.sp, color = Color.LightGray)
        Text("Pack Version: ${packData.packVersion}", fontSize = 12.sp, color = Color.LightGray)
        Spacer(Modifier.padding(8.dp))
    }
}

enum class PackManagerTabs(val tabName: String) {
    PACK_SELECTOR("Pack Selector"), PACK_DOWNLOADER("Pack Downloader")
}
