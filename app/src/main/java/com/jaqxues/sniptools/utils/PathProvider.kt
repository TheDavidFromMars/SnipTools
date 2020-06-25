package com.jaqxues.sniptools.utils

import android.os.Build
import android.os.Environment
import com.jaqxues.sniptools.CustomApplication
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import java.io.File
import java.util.*
import kotlin.collections.HashSet


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 18:20.
 */
object PathProvider {
    private var cachedExternalDir: String? = null

    val contentPath get() = getExternalPath() + "/" + CustomApplication.MODULE_TAG + "/"
    val databasesPath get() = contentPath + "Databases/"
    val modulesPath get() = contentPath + "ModulePack/"
    val sharedImagePath get() = contentPath + "SharedImages/"
    val tempPath get() = contentPath + "Temp/"
    val logsPath get() = contentPath + "ErrorLogs/"
    val backupPath get() = contentPath + "Backups/"
    val translationsPath get() = contentPath + "Translations/"

    const val PREF_FILE_NAME = "Preferences.json"

    private fun getExternalPath(): String {
        cachedExternalDir?.let { return it }
        cachedExternalDir = useExternalPathFallback()
        cachedExternalDir?.let { return it }

        try {
            cachedExternalDir = runBlocking {
                withTimeout(5000) {
                    for (externalMount in getExternalMounts()) {
                        if (isMounted(externalMount))
                            return@withTimeout externalMount
                    }

                    val externalPath = useExternalPathFallback()
                    if (externalPath != null && isMounted(externalPath))
                        return@withTimeout externalPath
                    return@withTimeout null
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        cachedExternalDir?.let { return it }
        throw IllegalStateException("Could not find mounted storage")
    }

    private fun getExternalMounts(): HashSet<String> {
        val result = HashSet<String>()
        val reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*".toRegex()
        val output = try {
            val process = ProcessBuilder()
                .command("mount")
                .redirectErrorStream(true)
                .start()
            process.waitFor()
            process.inputStream.bufferedReader().use {
                it.readLines()
            }
        } catch (e: Exception) {
            Timber.e(e, "Could not determine mounts from Process output")
            return result
        }
        for (line in output) {
            if ("asec" !in line.toLowerCase(Locale.US) && reg matches line) {
                for (part in line.split(" ")) {
                    if (part.startsWith("/") && "vold" !in part.toLowerCase(Locale.US)) {
                        result.add(part)
                    }
                }
            }
        }
        return result
    }

    private fun useExternalPathFallback(): String? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            try {
                val environmentCls = Class.forName("android.os.Environment")
                val setUserRequired = environmentCls.getMethod("setUserRequired", Boolean::class.javaPrimitiveType!!)
                setUserRequired.invoke(null, false)
            } catch (e: Exception) {
                Timber.e(e, "Could not get External Path")
            }
        }
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState())
            return null

        return Environment.getExternalStorageDirectory().absolutePath
    }

    private fun isMounted(path: String) = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState(File(path))
}