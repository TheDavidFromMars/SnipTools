package com.jaqxues.sniptools.db

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:39.
 */
@Database(entities = arrayOf(ServerPackEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun packDao(): PackDao
}