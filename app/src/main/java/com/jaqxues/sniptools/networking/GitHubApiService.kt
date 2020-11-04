package com.jaqxues.sniptools.networking

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
    suspend fun getServerPacks(): List<ServerPack>

    @GET("Packs/Info/KnownBugs/KnownBugs_Sc_v{scVersion}.json")
    suspend fun getKnownBugsFor(@Path("scVersion") scVersion: String): Map<String, List<KnownBug>>

    @GET("Packs/Info/History/History_Sc_v{scVersion}.json")
    suspend fun getHistoryFor(@Path("scVersion") scVersion: String): List<PackHistory>


    @GET("Apks/Info/ServerApks.json")
    suspend fun getLatestApk(): ServerApk


    @Streaming
    @GET("https://github.com/jaqxues/SnipTools_DataProvider/blob/master/Packs/Files/{packName}.jar?raw=true")
    suspend fun getPackFile(@Path("packName") packName: String): ResponseBody

    @Streaming
    @GET("https://github.com/jaqxues/SnipTools_DataProvider/blob/master/Apks/Files/{apk_name}.apk?raw=true")
    suspend fun getApkFile(@Path("apk_name") apkName: String): ResponseBody
}