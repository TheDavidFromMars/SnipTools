package com.jaqxues.sniptools.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jaqxues.sniptools.db.AppDatabase
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.pack.PackLoadManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:38.
 */
@InstallIn(ApplicationComponent::class)
@Module
object DataModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofitService(): GitHubApiService =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/jaqxues/SnipTools_DataProvider/master/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GitHubApiService::class.java)

    @Singleton
    @Provides
    fun providePackLoadManager() = PackLoadManager()

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context) =
        context.getSharedPreferences("main", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "main.db").build()

    @Singleton
    @Provides
    fun providePackDao(database: AppDatabase) = database.packDao()

    @Singleton
    @Provides
    fun provideKnownBugsDao(database: AppDatabase) = database.knownBugDao()
}