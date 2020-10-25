package com.jaqxues.sniptools.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:38.
 */
@Dao
interface PackDao {
    @Query("SELECT * FROM serverpackentity")
    fun getAllPacks(): LiveData<List<ServerPackEntity>>

    @Insert
    fun insertAll(vararg packs: ServerPackEntity)

    @Query("DELETE FROM serverpackentity")
    fun deleteAllPacks()

    @Transaction
    fun updateAll(vararg packs: ServerPackEntity) {
        deleteAllPacks()
        insertAll(*packs)
    }
}