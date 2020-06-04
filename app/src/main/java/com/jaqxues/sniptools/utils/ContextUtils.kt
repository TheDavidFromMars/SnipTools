package com.jaqxues.sniptools.utils

import android.content.Context
import android.content.pm.PackageManager
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.06.20 - Time 10:35.
 */
val Context.installedScVersion get() =
    try {
        packageManager
            .getPackageInfo("com.snapchat.android", 0)
            .versionName
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e, "Unable to locate Snapchat on this device")
        null
    }