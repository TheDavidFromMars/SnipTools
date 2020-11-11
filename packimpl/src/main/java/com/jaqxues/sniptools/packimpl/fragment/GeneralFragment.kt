package com.jaqxues.sniptools.packimpl.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaqxues.akrolyb.prefs.edit
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.packimpl.PackImpl
import com.jaqxues.sniptools.packimpl.features.*
import com.jaqxues.sniptools.packimpl.utils.CheckboxCard
import com.jaqxues.sniptools.packimpl.utils.FeatureSet
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.DISABLED_FEATURES


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project Instaprefs.<br>
 * Date: 16.10.20 - Time 20:02.
 */
@Composable
fun GeneralScreen() {
    Column(Modifier.padding(16.dp)) {
        var disabledFeatures by remember { mutableStateOf(DISABLED_FEATURES.getPref()) }
        mapOf(
            MiscFeatures::class to Destinations.MISC,
            SavingFeature::class to Destinations.SAVING,
            ScreenshotBypass::class to Destinations.SCREENSHOT,
            StealthViewing::class to Destinations.STEAlTH,
            UnlimitedViewing::class to Destinations.UNLIMITED
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
                    PackImpl.disabledFeatures.value = disabledFeatures
                }
            ) {
                Text(v.destination.defaultName)
            }
            Spacer(Modifier.padding(8.dp))
        }
    }
}
