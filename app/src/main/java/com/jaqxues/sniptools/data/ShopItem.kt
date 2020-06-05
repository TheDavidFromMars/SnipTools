package com.jaqxues.sniptools.data


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 20:45.
 */
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