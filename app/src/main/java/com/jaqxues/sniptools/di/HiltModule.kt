package com.jaqxues.sniptools.di

import android.content.Context
import androidx.room.Room
import com.jaqxues.sniptools.db.AppDatabase
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.pack.PackLoadManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:38.
 */
@InstallIn(ApplicationComponent::class)
@Module
object DataModule {
    @Singleton
    @Provides
    fun provideRetrofitService(): GitHubApiService =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/jaqxues/SnipTools_DataProvider/master/")
            .addConverterFactory(GsonConverterFactory.create())
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