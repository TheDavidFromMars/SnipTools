package com.jaqxues.sniptools.packimpl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.jaqxues.sniptools.fragments.PackFragment
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.ASK_SCREENSHOT_CONFIRMATION
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.packimpl.utils.TitleAndDescription
import com.jaqxues.sniptools.ui.AppScreen


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 11.10.20 - Time 12:16.
 */
class ScreenshotFragment: PackFragment() {
    override val name = "Screenshot Bypass"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                Column(Modifier.padding(16.dp)) {
                    SwitchPreference(ASK_SCREENSHOT_CONFIRMATION) {
                        TitleAndDescription(
                            title = "Confirm Screenshot Notification",
                            description = "Allows you to chose whether to send the screenshot notification"
                        )
                    }
                }
            }
        }
    }
}
