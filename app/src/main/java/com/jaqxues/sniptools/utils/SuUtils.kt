package com.jaqxues.sniptools.utils

import com.topjohnwu.superuser.Shell
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 31.10.20 - Time 09:23.
 */
object SuUtils {
    suspend fun runSuCommands(vararg commands: String): Shell.Result {
        return suspendCoroutine { cont ->
            Shell.su(*commands).submit { shellResult ->
                cont.resume(shellResult)
            }
        }
    }
}