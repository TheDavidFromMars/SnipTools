package com.jaqxues.sniptools.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.jaqxues.sniptools.CustomApplication
import timber.log.Timber
import java.lang.ref.WeakReference


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 23.06.20 - Time 12:56.
 */
object ContextContainer {
    private var moduleContextRef: WeakReference<Context>? = null
    private var activityRef: WeakReference<Activity>? = null

    fun setModuleContext(moduleContext: Context) {
        moduleContextRef = WeakReference(moduleContext)
    }

    fun getModuleContext(appContext: Context? = null): Context? {
        var moduleContext = moduleContextRef?.get()
        if (moduleContext == null) {
            appContext?.let { ctx ->
                moduleContext = createModuleContext(ctx)
            }
        }
        return moduleContext
    }

    fun getModuleContextNotNull(appContext: Context? = null) = getModuleContext(appContext)
        ?: throw IllegalStateException("Module Context is null")

    fun createModuleContext(appContext: Context): Context {
        try {
            return appContext.createPackageContext(
                CustomApplication.PACKAGE_NAME, Context.CONTEXT_IGNORE_SECURITY
            )
        } catch (e: PackageManager.NameNotFoundException) {
            val i =  IllegalStateException("Could not create Package Context", e)
            Timber.e(i)
            throw i
        }
    }

    fun setActivity(activity: Activity) { activityRef = WeakReference(activity) }
    fun getActivity() = activityRef?.get()
}