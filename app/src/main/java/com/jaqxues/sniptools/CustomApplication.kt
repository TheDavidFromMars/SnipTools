package com.jaqxues.sniptools

import android.app.Application
import com.jaqxues.sniptools.di.repositories
import com.jaqxues.sniptools.di.services
import com.jaqxues.sniptools.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:58.
 */
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.d("Initializing Application")

        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(viewModels, repositories, services)
        }
    }

    companion object {
        const val MODULE_TAG = "SnipTools"

        const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    }
}