package com.jaqxues.sniptools.utils

import android.util.Log
import com.jaqxues.akrolyb.logger.FileLogger
import com.jaqxues.akrolyb.prefs.PrefManager
import com.jaqxues.sniptools.BuildConfig
import com.jaqxues.sniptools.data.Preferences
import timber.log.Timber
import java.io.File


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 25.06.20 - Time 20:37.
 */
object CommonSetup {
    fun initPrefs() {
        PrefManager.init(
            File(PathProvider.contentPath, PathProvider.PREF_FILE_NAME),
            Preferences::class
        )
    }

    fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {

            Timber.plant(object: Timber.DebugTree() {
                override fun isLoggable(priority: Int): Boolean {
                    return priority >= Log.WARN
                }
            })
            // External File Logging
//            ileLogger(FileLogger.getLogFile(File(PathProvider.logsPath))).run {
//                startLogger()
//                Timber.plant(this)
//            }
        }
    }
}