package com.jaqxues.sniptools.db

import androidx.room.TypeConverter
import java.util.*


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 26.10.20 - Time 18:06.
 */
class CustomTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return Date(value ?: return null)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) =
        date?.time
}