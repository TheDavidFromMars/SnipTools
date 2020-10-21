package com.jaqxues.sniptools.packimpl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.jaqxues.akrolyb.prefs.edit
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.fragments.PackFragment
import com.jaqxues.sniptools.packimpl.features.*
import com.jaqxues.sniptools.packimpl.utils.CheckboxCard
import com.jaqxues.sniptools.packimpl.utils.FeatureSet
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.DISABLED_FEATURES
import com.jaqxues.sniptools.ui.AppScreen


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project Instaprefs.<br>
 * Date: 16.10.20 - Time 20:02.
 */

class GeneralFragment : PackFragment() {
    override val name = "General"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppScreen {
                Column(Modifier.padding(16.dp)) {
                    var disabledFeatures by remember { mutableStateOf(DISABLED_FEATURES.getPref()) }
                    mapOf(
                        MiscFeatures::class to "Misc Features",
                        SavingFeature::class to "Saving",
                        ScreenshotBypass::class to "Screenshot Bypass",
                        StealthViewing::class to "Stealth Viewing",
                        UnlimitedViewing::class to "Unlimited Viewing"
                    ).forEach { (k, v) ->
                        val featureKey =
                            FeatureSet.optionalFeatures.entries.find { (_, v) -> v == k }?.key
                                ?: error("Feature Key cannot be null")
                        CheckboxCard(
                            toggled = featureKey !in disabledFeatures,
                            onToggle = { enabled ->
                                disabledFeatures = DISABLED_FEATURES.edit {
                                    if (enabled) it - featureKey else it + featureKey
                                }
                                FeatureSet.disabledFeatures.value = disabledFeatures
                            }
                        ) {
                            Text(v)
                        }
                        Spacer(Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}
