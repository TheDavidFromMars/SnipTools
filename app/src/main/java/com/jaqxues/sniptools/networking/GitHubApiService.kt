package com.jaqxues.sniptools.networking

import com.jaqxues.sniptools.data.ServerPackMetadata
import com.jaqxues.sniptools.data.ShopItem
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.06.20 - Time 20:16.
 */
interface GitHubApiService {
    @GET("Packs/Info/ServerPacks.json")
    suspend fun getServerPacks(): List<ServerPackMetadata>

    @GET("/General/ShopItems.json")
    suspend fun getShopItems(): List<ShopItem>


    @GET("/Packs/JSON/History/PackHistory_SC_v{scVersion}.json")
    suspend fun getPackHistory(@Path("scVersion") scVersion: String)

    @GET("/Packs/JSON/ChangeLog/PackChangelog_SC_v{scVersion}.json")
    suspend fun getPackChangelog(@Path("scVersion") scVersion: String)


    @GET("/Packs/JSON/PackUpdates/LatestPack_SC_v{scVersion}.json")
    suspend fun getLatestPack()

    @GET("/Apks/JSON/APKUpdates/LatestApkVersion.json")
    suspend fun getLatestReleaseApkVersion()

    @GET("/Apks/JSON/APKUpdates/LatestBetaApkVersion.json")
    suspend fun getLatestBetaApkVersion()


    @Streaming
    @GET("/General/Features.txt")
    suspend fun getFeatures(): ResponseBody

    @Streaming
    @GET("/General/FAQs.txt")
    suspend fun getFAQs(): ResponseBody


    @Streaming
    @GET("https://github.com/jaqxues/SnipTools_DataProvider/blob/master/Packs/Files/{packName}.jar?raw=true")
    suspend fun getPackFile(@Path("packName") packName: String): ResponseBody

    @Streaming
    @GET("https://github.com/jaqxues/SnapTools_DataProvider/blob/master/Apks/Files/SnapTools-{flavour}.apk?raw=true")
    suspend fun getApkFile(@Path("flavour") flavour: String): ResponseBody
}