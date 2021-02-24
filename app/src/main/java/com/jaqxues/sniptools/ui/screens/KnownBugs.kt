package com.jaqxues.sniptools.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaqxues.sniptools.db.KnownBugEntity
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.ui.viewmodel.KnownBugsViewModel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.10.20 - Time 19:19.
 */
@Composable
fun KnownBugsScreen(scVersion: String, packVersion: String, bugsViewModel: KnownBugsViewModel) {
    val bugs by bugsViewModel.getBugsFor(scVersion, packVersion).observeAsState()

    BugsContent(bugs)
}

@Composable
fun BugsContent(bugs: List<KnownBugEntity>?) {
    if (bugs.isNullOrEmpty()) {
        EmptyScreenMessage("No Bugs listed")
        return
    }

    LazyColumn(Modifier.padding(16.dp)) {
        items(bugs.groupBy { it.category }.toList()) { (category, bugs) ->
            ListCardElement {
                Column(Modifier.padding(16.dp)) {
                    Text(category)
                    Divider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colors.primary)

                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        for (bug in bugs) {
                            Row(Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
                                Text(
                                    "\u2022",
                                    modifier = Modifier.padding(end = 16.dp),
                                    fontSize = 14.sp
                                )
                                Text(bug.description, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}