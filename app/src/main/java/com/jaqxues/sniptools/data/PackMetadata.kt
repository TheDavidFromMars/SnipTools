package com.jaqxues.sniptools.data

import com.google.gson.annotations.SerializedName
import com.jaqxues.akrolyb.pack.IPackMetadata


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.05.20 - Time 08:54.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:45.
 */
data class ServerPackMetadata(
    @SerializedName("sc_version")
    val scVersion: String,
    @SerializedName("name")
    val name: String,

    val devPack: Boolean = false,
    @SerializedName("pack_version")
    val packVersion: String,
    @SerializedName("pack_v_code")
    val packVersionCode: Int,
    @SerializedName("min_apk_v_code")
    val minApkVersionCode: Int
)

data class PackMetadata(
    val flavour: String,
    val scVersion: String,
    val name: String,

    override val devPack: Boolean,
    override val packVersion: String,
    override val packVersionCode: Int,
    override val packImplClass: String,
    override val minApkVersionCode: Int
): IPackMetadata
