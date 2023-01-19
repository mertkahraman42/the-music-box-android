package com.mertkahraman.themusicbox.di

import com.mertkahraman.themusicbox.data.database.TmbDatabase
import com.mertkahraman.themusicbox.data.model.MbEntity
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.RepositoryImpl
import com.mertkahraman.themusicbox.ui.artist.details.ArtistDetailsViewModel
import com.mertkahraman.themusicbox.ui.artist.search.ArtistSearchViewModel
import com.mertkahraman.themusicbox.ui.components.PaginatedListViewModel
import com.mertkahraman.themusicbox.ui.releasegroup.ReleaseGroupViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {

    // Database
    single { TmbDatabase.create(androidContext()) }

    // DAO
    single { get<TmbDatabase>().artistDao() }
    single { get<TmbDatabase>().releaseGroupDao() }

    // Repository
    single { RepositoryImpl(get(), get(), get()) as Repository }

    // ViewModels
    viewModel { ArtistSearchViewModel(get()) }
    viewModel { (artistMbid: String) -> ArtistDetailsViewModel(get(), artistMbid) }
    viewModel { (ownerArtistMbid: String) -> ReleaseGroupViewModel(get(), ownerArtistMbid) }
    viewModel { PaginatedListViewModel<MbEntity>(get()) }
}
