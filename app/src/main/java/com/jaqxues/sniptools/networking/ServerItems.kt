package com.jaqxues.sniptools.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.05.20 - Time 08:54.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:45.
 */
@Serializable
data class ServerPack(
    @SerialName("sc_version")
    val scVersion: String,
    @SerialName("name")
    val name: String,

    val devPack: Boolean = false,
    @SerialName("pack_version")
    val packVersion: String,
    @SerialName("pack_v_code")
    val packVersionCode: Int,
    @SerialName("min_apk_v_code")
    val minApkVersionCode: Int,
    @SerialName("changelog")
    val changelog: String
)

@Serializable
data class KnownBug(
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("filed_on")
    val filedOn: Long,
    @SerialName("fixed_on")
    val fixedOn: Long? = null
)

@Serializable
data class ServerApk(
    @SerialName("name")
    val name: String,
    @SerialName("apk_v_code")
    val versionCode: Int,
    @SerialName("apk_v_name")
    val versionName: String,
    @SerialName("created_at")
    val createdAt: Long
)

@Serializable
data class PackHistory(
    @SerialName("name")
    val name: String,

    val devPack: Boolean = false,
    @SerialName("pack_version")
    val packVersion: String,
    @SerialName("pack_v_code")
    val packVersionCode: Int,
    @SerialName("min_apk_v_code")
    val minApkVersionCode: Int,
    @SerialName("created_at")
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
