package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.text.format.DateUtils
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
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabConstants.defaultTabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.AppScreen
import com.jaqxues.sniptools.utils.viewModel
import com.jaqxues.sniptools.viewmodel.PackViewModel

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
    AppScreen {
        Column {
            var currentTab by remember { mutableStateOf(PackManagerTabs.PACK_SELECTOR) }
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

            val packViewModel by viewModel<PackViewModel>()
            Crossfade(current = currentTab) {
                when (it) {
                    PackManagerTabs.PACK_SELECTOR -> PackSelectorTab(packViewModel)
                    PackManagerTabs.PACK_DOWNLOADER -> PackDownloaderTab(packViewModel)
                }
            }
        }
    }
}

@Composable
fun PackSelectorTab(packViewModel: PackViewModel) {
    val localPacks by packViewModel.localPacks.observeAsState()
    LazyColumnFor(items = localPacks!!) {
        PackElement(it)
    }
}

@Composable
fun PackDownloaderTab(packViewModel: PackViewModel) {
    val serverPacks by packViewModel.serverPacks.observeAsState()
    val lastChecked by packViewModel.lastChecked.observeAsState()

    Column(Modifier.fillMaxHeight(), horizontalGravity = Alignment.CenterHorizontally) {
        LazyColumnFor(items = serverPacks!!, modifier = Modifier.weight(1f)) {
            PackElement(it)
        }

        Divider(Modifier.padding(48.dp, 8.dp, 48.dp), color = colorResource(id = R.color.colorPrimary))
        Text(annotatedString {
            append("Last Checked: ")
            withStyle(SpanStyle(colorResource(R.color.colorPrimaryLight))) {
                append(DateUtils.getRelativeDateTimeString(
                    ContextAmbient.current, lastChecked!!,
                    DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                ).toString())
            }
        }, modifier = Modifier.padding(8.dp), color = Color.LightGray, fontSize = 12.sp)
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
        var isActive by remember { mutableStateOf(false) }
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
                        .gravity(Alignment.CenterVertically)
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
                        .preferredHeight(24.dp),
                    tint = animate(if (isActive) Color(0xFF00AA00) else Color.White)
                )
                Text(
                    text = "Pack v$packName"
                )
            }
            AnimatedVisibility(visible = extendedPack) {
                ExtendedPackLayout(packData, isActive) { isActive = it }
            }
        }
    }
}

@Composable
fun ExtendedPackLayout(packData: PackData, isActive: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
        Divider(Modifier.padding(horizontal = 20.dp))
        Row(Modifier.gravity(Alignment.CenterHorizontally), verticalGravity = Alignment.CenterVertically) {
            IconButton(onClick = {}) {
                Image(
                    vectorResource(id = R.drawable.ic_baseline_history_48),
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.preferredHeight(40.dp).padding(8.dp)
                )
            }

            Switch(isActive, onCheckedChange = onCheckedChange, color = Color(0xFF00AA00), modifier = Modifier.padding(horizontal = 16.dp))

            IconButton(onClick = {}) {
                Image(
                    vectorResource(id = R.drawable.ic_baseline_remove_circle_outline_48),
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.preferredHeight(40.dp).padding(8.dp)
                )
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
