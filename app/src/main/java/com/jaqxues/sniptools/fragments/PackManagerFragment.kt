package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabConstants.defaultTabIndicatorOffset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
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
                                    Card(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .fillMaxWidth(),
                                        elevation = 4.dp
                                    ) {
                                        Row(
                                            Modifier
                                                .clickable(onClick = {})
                                                .padding(16.dp)
                                        ) {
                                            Icon(
                                                vectorResource(id = R.drawable.ic_pack),
                                                Modifier.padding(horizontal = 16.dp)
                                                    .preferredHeight(24.dp)
                                            )
                                            Text(
                                                text = "Pack v$it",
                                                color = Color.LightGray,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class PackManagerTabs(val tabName: String) {
    PACK_SELECTOR("Pack Selector"), PACK_DOWNLOADER("Pack Downloader")
}
