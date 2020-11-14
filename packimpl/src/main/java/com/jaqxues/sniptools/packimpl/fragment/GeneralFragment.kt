package com.jaqxues.sniptools.packimpl.fragment

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import com.jaqxues.akrolyb.prefs.edit
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.packimpl.PackImpl
import com.jaqxues.sniptools.packimpl.features.*
import com.jaqxues.sniptools.packimpl.utils.CheckboxCard
import com.jaqxues.sniptools.packimpl.utils.FeatureSet
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.DISABLED_FEATURES
import com.jaqxues.sniptools.utils.PrefEntries
import com.jaqxues.sniptools.utils.SuUtils
import com.jaqxues.sniptools.utils.getBoolean


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
            val ctx = ContextAmbient.current
            CheckboxCard(
                toggled = featureKey !in disabledFeatures,
                onToggle = { enabled ->
                    disabledFeatures = DISABLED_FEATURES.edit {
                        if (enabled) it - featureKey else it + featureKey
                    }
                    PackImpl.disabledFeatures.value = disabledFeatures
                    if ((ctx as Activity).getSharedPreferences("main", Context.MODE_PRIVATE)
                            .getBoolean(PrefEntries.shouldKillSc)
                    ) {
                        SuUtils.killScAndShow(ctx)
                    }
                }
            ) {
                Text(v.destination.defaultName)
            }
            Spacer(Modifier.padding(8.dp))
        }
    }
}
