package com.jaqxues.sniptools.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jaqxues.akrolyb.pack.AppData
import com.jaqxues.sniptools.db.AppDatabase
import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.pack.PackLoadManager
import com.jaqxues.sniptools.repository.KnownBugsRepo
import com.jaqxues.sniptools.repository.PackRepository
import com.jaqxues.sniptools.viewmodel.KnownBugsViewModel
import com.jaqxues.sniptools.viewmodel.PackViewModel
import com.jaqxues.sniptools.viewmodel.ServerPackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:38.
 */
object KoinModules {
    val viewModels = module {
        viewModel { PackViewModel(get()) }
        viewModel { ServerPackViewModel(get()) }
        viewModel { KnownBugsViewModel(get()) }
    }

    val repositories = module {
        single { PackRepository(get(), get(), get(), get()) }
        single { KnownBugsRepo(get(), get()) }
    }

    val services = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/jaqxues/SnipTools_DataProvider/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApiService::class.java)
        }

        single { PackLoadManager() }
        factory { androidContext().getSharedPreferences("main", Context.MODE_PRIVATE) }
    }

    val databases = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java, "main.db"
            ).build()
        }

        single { get<AppDatabase>().packDao() }
        single { get<AppDatabase>().knownBugDao() }
    }
}