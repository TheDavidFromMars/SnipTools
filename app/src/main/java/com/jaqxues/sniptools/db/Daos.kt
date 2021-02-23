package com.jaqxues.sniptools.db

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:38.
 */
@Dao
interface PackDao {
    @Query(
        """
        SELECT SPE1.* FROM ServerPackEntity AS SPE1 WHERE pack_id IN (
            SELECT SPE2.pack_id FROM ServerPackEntity AS SPE2
            WHERE SPE1.sc_version = SPE2.sc_version
            ORDER BY SPE2.pack_v_code DESC, SPE2.created_at DESC
            LIMIT 1
        )
    """
    )
    fun getLatestServerPacks(): LiveData<List<ServerPackEntity>>

    @Query("SELECT * FROM ServerPackEntity WHERE sc_version=:scVersion ORDER BY pack_v_code DESC, created_at DESC")
    fun getPackHistory(scVersion: String): LiveData<List<ServerPackEntity>>

    @Insert
    fun insertAll(vararg packs: ServerPackEntity)

    @Query("DELETE FROM ServerPackEntity")
    fun deleteAllPacks()

    @Query("DELETE FROM ServerPackEntity WHERE sc_version=:scVersion")
    fun deleteAllPacksFor(scVersion: String)

    @Transaction
    fun updateAll(vararg packs: ServerPackEntity) {
        deleteAllPacks()
        insertAll(*packs)
    }

    @Transaction
    fun updateAllFor(scVersion: String, vararg packs: ServerPackEntity) {
        deleteAllPacksFor(scVersion)
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBugCrossRef(crossRef: BugCrossRef)

    @Query("DELETE FROM KnownBugEntity")
    fun deleteAllBugs()

    @Transaction
    fun updateBugs(scVersion: String, packVersion: String, vararg bugs: KnownBugEntity) {
        deleteBugRefsFor(scVersion, packVersion)

        for (id in insertBugs(*bugs))
            insertBugCrossRef(BugCrossRef(scVersion, packVersion, id))
    }
}
