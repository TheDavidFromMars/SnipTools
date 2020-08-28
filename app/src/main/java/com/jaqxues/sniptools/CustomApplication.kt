package com.jaqxues.sniptools

import android.app.Application
import com.jaqxues.sniptools.di.KoinModules.repositories
import com.jaqxues.sniptools.di.KoinModules.services
import com.jaqxues.sniptools.di.KoinModules.viewModels
import com.jaqxues.sniptools.utils.CommonSetup
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:58.
 */
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CommonSetup.initTimber()
        Timber.d("Initializing Application")

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CustomApplication)
            modules(viewModels, repositories, services)
        }
    }

    companion object {
        const val MODULE_TAG = "SnipTools"

        const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    }
}