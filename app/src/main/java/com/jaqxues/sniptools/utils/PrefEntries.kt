package com.jaqxues.sniptools.utils

import android.content.SharedPreferences

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.11.20 - Time 19:45.
 */

data class PrefEntry<T>(val key: String, val default: T)

fun SharedPreferences.getBoolean(prefEntry: PrefEntry<Boolean>) = getBoolean(prefEntry.key, prefEntry.default)

object PrefEntries {
    val shouldKillSc = PrefEntry("should_kill_sc", false)
}