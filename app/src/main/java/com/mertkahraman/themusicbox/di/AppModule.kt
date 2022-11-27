package com.mertkahraman.themusicbox.di

import com.mertkahraman.themusicbox.data.database.TmbDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {

    // Database
    single { TmbDatabase.create(androidContext()) }

    // DAO
    single { get<TmbDatabase>().artistDao() }
}
