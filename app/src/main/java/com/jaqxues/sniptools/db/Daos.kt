package com.jaqxues.sniptools.db

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:38.
 */
@Dao
interface PackDao {
    @Query("SELECT * FROM ServerPackEntity")
    fun getAllPacks(): LiveData<List<ServerPackEntity>>

    @Insert
    fun insertAll(vararg packs: ServerPackEntity)

    @Query("DELETE FROM ServerPackEntity")
    fun deleteAllPacks()

    @Transaction
    fun updateAll(vararg packs: ServerPackEntity) {
        deleteAllPacks()
        insertAll(*packs)
    }
}

@Dao
interface KnownBugDao {
    @Transaction
    @Query("SELECT * FROM ServerPackEntity WHERE pack_id=:packId")
    fun getBugsFor(packId: Long): LiveData<PackWithBugs>

    @Query("DELETE FROM BugCrossRef WHERE pack_id=:packId")
    fun deleteBugsFor(packId: Long)

    @Insert
    fun insertBugs(vararg bugs: KnownBugEntity): List<Long>

    @Transaction
    fun updateFor(packId: Long, vararg bugs: KnownBugEntity) {
        createCrossRefs(*insertBugs(*bugs).map { BugCrossRef(packId, it) }.toTypedArray())
    }

    @Insert
    fun createCrossRefs(vararg crossRefs: BugCrossRef)

    @Query("DELETE FROM KnownBugEntity")
    fun deleteAllBugs()
}
