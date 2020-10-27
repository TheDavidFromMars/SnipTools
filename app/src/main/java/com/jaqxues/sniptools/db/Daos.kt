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
    @Query("SELECT * FROM BugCrossRef JOIN KnownBugEntity WHERE sc_version=:scVersion AND pack_version=:packVersion")
    fun getBugsFor(scVersion: String, packVersion: String): LiveData<List<KnownBugEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBugs(vararg bugs: KnownBugEntity): List<Long>

    @Query("DELETE FROM BugCrossRef WHERE sc_version=:scVersion AND pack_version=:packVersion")
    fun deleteBugRefsFor(scVersion: String, packVersion: String)

    @Query("DELETE FROM KnownBugEntity")
    fun deleteAllBugs()

    @Transaction
    fun updateBugs(scVersion: String, packVersion: String, vararg bugs: KnownBugEntity) {
        deleteBugRefsFor(scVersion, packVersion)
        insertBugs(*bugs)
    }
}
