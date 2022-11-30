package com.mertkahraman.themusicbox.di

import com.mertkahraman.themusicbox.data.database.TmbDatabase
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.RepositoryImpl
import com.mertkahraman.themusicbox.ui.artist.search.ArtistSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {

    // Database
    single { TmbDatabase.create(androidContext()) }

    // DAO
    single { get<TmbDatabase>().artistDao() }

    // Repository
    single { RepositoryImpl(get(), get()) as Repository }

    // ViewModels
    viewModel { ArtistSearchViewModel(get()) }
}
