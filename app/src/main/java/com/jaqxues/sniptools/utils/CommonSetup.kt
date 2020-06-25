package com.jaqxues.sniptools.utils

import com.jaqxues.akrolyb.prefs.PrefManager
import com.jaqxues.sniptools.data.Preferences
import timber.log.Timber
import java.io.File


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 25.06.20 - Time 20:37.
 */
object CommonSetup {
    fun initPrefs() {
        PrefManager.init(File(PathProvider.contentPath, PathProvider.PREF_FILE_NAME), Preferences::class)
    }

    fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}