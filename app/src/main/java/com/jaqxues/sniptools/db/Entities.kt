package com.jaqxues.sniptools.db

import androidx.room.*
import java.util.*

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 24.10.20 - Time 20:35.
 */
@Entity
data class ServerPackEntity(
    @ColumnInfo(name = "sc_version") val scVersion: String,
    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "dev_pack") val devPack: Boolean = false,
    @ColumnInfo(name = "pack_version") val packVersion: String,
    @ColumnInfo(name = "pack_v_code") val packVersionCode: Int,
    @ColumnInfo(name = "min_apk_v_code") val minApkVersionCode: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pack_id") val id: Long
)

@Entity(primaryKeys = ["pack_id", "bug_id"])
data class BugCrossRef(val pack_id: Long, val bug_id: Long)

@Entity
data class KnownBugEntity(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "filed_on") val filedOn: Date,
    @ColumnInfo(name = "fixed_on") val fixedOn: Date? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bug_id") val id: Long
)

data class PackWithBugs(
    @Embedded val pack: ServerPackEntity,
    @Relation(parentColumn = "pack_id", entityColumn = "bug_id", associateBy = Junction(BugCrossRef::class))
    val bugs: List<KnownBugEntity>
)
