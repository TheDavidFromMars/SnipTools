package com.jaqxues.sniptools.packimpl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.fragments.PackFragment
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.DISABLE_CAPTION_LENGTH_LIMIT
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.FORCE_SC_APP_DECK_MODE
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.ui.AppScreen

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 26.09.20 - Time 21:43.
 */
class MiscFragment : PackFragment() {
    override val name: String = "Misc Changes"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                Column(Modifier.padding(16.dp)) {
                    SwitchPreference(FORCE_SC_APP_DECK_MODE) {
                        Text("Disable Bottom App Bar")
                    }
                    Spacer(Modifier.padding(8.dp))
                    SwitchPreference(DISABLE_CAPTION_LENGTH_LIMIT) {
                        Text("Disable Caption Length Limit")
                    }
                }
            }
        }
    }
}