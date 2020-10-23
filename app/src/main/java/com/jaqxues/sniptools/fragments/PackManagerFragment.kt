package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animate
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandablePackLayout(
    packName: String,
    color: Color = AmbientContentColor.current,
    extendedContent: @Composable () -> Unit
) {
    var extended by remember { mutableStateOf(false) }
    ListCardElement(onClick = { extended = !extended }) {
        Column {
            Row(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val rotation = animate(if (extended) 0f else 180f)

                Icon(
                    vectorResource(id = R.drawable.ic_pack),
                    Modifier.padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
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
                    tint = color
                )
                Text(
                    text = packName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            AnimatedVisibility(visible = extended) {
                extendedContent()
            }
        }
    }
}

@Composable
fun ListCardElement(onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .run { onClick?.let { clickable(onClick = it) } ?: this }
            .fillMaxWidth(), elevation = 2.dp) {
        content()
    }
}

enum class PackManagerTabs(val tabName: String) {
    PACK_SELECTOR("Pack Selector"), PACK_DOWNLOADER("Pack Downloader")
}
