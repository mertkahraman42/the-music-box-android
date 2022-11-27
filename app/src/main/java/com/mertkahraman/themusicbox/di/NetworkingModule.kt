package com.mertkahraman.themusicbox.di

import com.mertkahraman.themusicbox.data.api.util.MbHeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://musicbrainz.org/ws/2/"

fun networkingModule() = module {

    // Client: OkHttp
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<MbHeaderInterceptor>())
            .addInterceptor(
                get<HttpLoggingInterceptor>().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
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
