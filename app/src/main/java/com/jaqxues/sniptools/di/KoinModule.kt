package com.jaqxues.sniptools.di

import com.jaqxues.sniptools.networking.GitHubApiService
import com.jaqxues.sniptools.repository.PackRepository
import com.jaqxues.sniptools.viewmodel.PackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:38.
 */
val viewModels = module {
    viewModel {
        PackViewModel(get())
    }
}

val repositories = module {
    single { PackRepository(get()) }
}

val services = module {
    single { Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/jaqxues/SnapTools_DataProvider/master/")
        .build()
        .create(GitHubApiService::class.java)
    }
}