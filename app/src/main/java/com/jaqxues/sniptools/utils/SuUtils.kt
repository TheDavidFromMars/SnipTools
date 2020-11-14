package com.jaqxues.sniptools.utils

import android.content.Context
import android.widget.Toast
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 31.10.20 - Time 09:23.
 */
object SuUtils {
    suspend fun runKillCommand(packageName: String) =
        runSuCommands("am force-stop $packageName")

    suspend fun runSuCommands(vararg commands: String): Shell.Result {
        return suspendCoroutine { cont ->
            Shell.su(*commands).submit { shellResult ->
                cont.resume(shellResult)
            }
        }
    }

    suspend fun installApk(apkFile: File): Shell.Result {
        return suspendCoroutine { cont ->
            Shell.su("pm install ${apkFile.absolutePath}").submit { shellResult ->
                cont.resume(shellResult)
            }
        }
    }

    fun killScAndShow(ctx: Context) {
        GlobalScope.launch(Dispatchers.Default) {
            val result = runKillCommand("com.snapchat.android")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    ctx, if (result.isSuccess) "Killed Snapchat" else "Failed to kill Snapchat",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}