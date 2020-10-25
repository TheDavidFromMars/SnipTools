package com.jaqxues.sniptools.utils

import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isEmpty
import androidx.core.view.size

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 08.10.20 - Time 17:02.
 */

@OptIn(ExperimentalStdlibApi::class)
val Menu.items: List<MenuItem>
    get() {
        if (isEmpty())
            return emptyList()
        return buildList {
            for (x in 0 until this@items.size)
                add(getItem(x))
        }
    }

val Long.formatRelativeAbbrev: String
    get() {
        return if (this < 0)
            "Never"
        else
            DateUtils.getRelativeTimeSpanString(
                this, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE).toString()
    }
