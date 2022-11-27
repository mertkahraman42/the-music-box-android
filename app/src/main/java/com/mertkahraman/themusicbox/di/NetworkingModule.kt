package com.mertkahraman.themusicbox.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://musicbrainz.org/ws/2/"

fun networkingModule() = module {

    // Client: OkHttp
    single {
        OkHttpClient.Builder()
            .build()
    }

    // Gson
    single { GsonConverterFactory.create() }

    // Retrofit
    single {
        Retrofit.Builder()
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .baseUrl(BASE_URL)
            .build()
    }
}
