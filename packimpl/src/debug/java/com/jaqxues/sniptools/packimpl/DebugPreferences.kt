package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.prefs.Preference


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.07.20 - Time 23:03.
 */
object DebugPreferences {
    val DB_DEBUG_SERVER = Preference(
        "debug_db_server", true, Boolean::class
    )
}