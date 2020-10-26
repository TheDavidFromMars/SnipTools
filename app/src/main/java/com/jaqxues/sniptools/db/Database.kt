package com.jaqxues.sniptools.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:39.
 */
@Database(entities = arrayOf(ServerPackEntity::class, KnownBugEntity::class, BugCrossRef::class), version = 1)
@TypeConverters(CustomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun packDao(): PackDao
    abstract fun knownBugDao(): KnownBugDao
}