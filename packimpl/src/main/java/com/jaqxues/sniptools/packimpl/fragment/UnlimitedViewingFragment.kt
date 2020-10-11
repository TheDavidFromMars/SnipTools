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
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_IMAGE_UNLIMITED
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.SNAP_VIDEO_LOOPING
import com.jaqxues.sniptools.packimpl.utils.SwitchPreference
import com.jaqxues.sniptools.ui.AppScreen

class UnlimitedViewingFragment: PackFragment() {
    override val name = "Unlimited Viewing"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                Column(Modifier.padding(16.dp)) {
                    SwitchPreference(SNAP_VIDEO_LOOPING) {
                        Text("Loop Videos")
                    }
                    Spacer(Modifier.padding(8.dp))
                    SwitchPreference(SNAP_IMAGE_UNLIMITED) {
                        Text("Disable Snap Timer")
                    }
                }
            }
        }
    }
}
