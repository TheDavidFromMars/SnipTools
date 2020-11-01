package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.db.KnownBugEntity
import com.jaqxues.sniptools.pack.PackMetadata
import com.jaqxues.sniptools.ui.AppScreen
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.viewmodel.KnownBugsViewModel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.10.20 - Time 19:19.
 */
class KnownBugsFragment(private val packMetadata: PackMetadata) : BaseFragment() {
    override val menuId = R.id.nav_bugs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                KnownBugsScreen(packMetadata.scVersion, packMetadata.packVersion)
            }
        }
    }
}

@Composable
fun KnownBugsScreen(scVersion: String, packVersion: String) {
    val bugsViewModel: KnownBugsViewModel by viewModel()
    val bugs by bugsViewModel.getBugsFor(scVersion, packVersion).observeAsState()

    BugsContent(bugs)
}

@Composable
fun BugsContent(bugs: List<KnownBugEntity>?) {
    if (bugs.isNullOrEmpty()) {
        EmptyScreenMessage("No Bugs listed")
        return
    }

    LazyColumnFor(bugs, Modifier.padding(vertical = 16.dp)) { bug ->
        ListCardElement {
            Column(Modifier.padding(16.dp)) {
                Text(bug.category, color = MaterialTheme.colors.primary)
                Divider(Modifier.padding(vertical = 8.dp).padding(end = 80.dp))
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                    Text(bug.description, fontSize = 14.sp)
                }
            }
        }
    }
}