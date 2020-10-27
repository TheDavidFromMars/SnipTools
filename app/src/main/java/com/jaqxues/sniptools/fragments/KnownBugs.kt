package com.jaqxues.sniptools.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.jaqxues.sniptools.R
import com.jaqxues.sniptools.ui.AppScreen
import com.jaqxues.sniptools.ui.composables.EmptyScreenMessage
import com.jaqxues.sniptools.utils.viewModel
import com.jaqxues.sniptools.viewmodel.KnownBugsViewModel


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.10.20 - Time 19:19.
 */
class KnownBugsFragment: BaseFragment() {
    override val menuId = R.id.nav_bugs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                KnownBugsScreen()
            }
        }
    }
}
@Composable
fun KnownBugsScreen() {
    val bugsViewModel by viewModel<KnownBugsViewModel>()


    EmptyScreenMessage("No Bugs available")
}