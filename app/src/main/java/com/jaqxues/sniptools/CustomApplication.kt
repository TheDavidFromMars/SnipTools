package com.jaqxues.sniptools

import android.app.Application
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:58.
 */
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.d("Initializing Application")
    }

    companion object {
        const val MODULE_TAG = "SnipTools"
    }
}