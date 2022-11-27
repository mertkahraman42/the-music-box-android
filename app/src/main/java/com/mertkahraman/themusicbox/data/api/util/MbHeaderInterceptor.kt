package com.mertkahraman.themusicbox.data.api.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/*
 * Responsible for providing standard headers to communicate with MusicBrainz API.
 */
class MbHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val jsonRequest: Request = request.newBuilder()
            .header("Accept", "application/json")
            .header(
                "User-agent",
                "com.mertkahraman.themusicbox - " +
                    "github.com/mertkahraman42/the-music-box-android"
            )
            .build()
        return chain.proceed(jsonRequest)
    }
}
