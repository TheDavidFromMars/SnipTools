package com.jaqxues.sniptools.networking

import com.google.gson.annotations.SerializedName


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.05.20 - Time 08:54.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:45.
 */
data class ServerPack(
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

data class KnownBug(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("filed_on")
    val filedOn: Long,
    @SerializedName("fixed_on")
    val fixedOn: Long? = null
)

data class ServerApk(
    @SerializedName("name")
    val name: String,
    @SerializedName("apk_v_code")
    val versionCode: Int,
    @SerializedName("apk_v_name")
    val versionName: String,
    @SerializedName("created_at")
    val createdAt: Long
)

data class PackHistory(
    @SerializedName("name")
    val name: String,

    val devPack: Boolean = false,
    @SerializedName("pack_version")
    val packVersion: String,
    @SerializedName("pack_v_code")
    val packVersionCode: Int,
    @SerializedName("min_apk_v_code")
    val minApkVersionCode: Int,
    @SerializedName("created_at")
    val createdAt: Long
)

data class ShopItem(
    val id: String,
    val type: ShopItemType,
    val price: Double,
    val description: String,
    val purchaseLink: String,
    val purchased: Boolean
)

enum class ShopItemType {
    DONATION, PACK
}
