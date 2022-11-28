package com.mertkahraman.themusicbox.data.api

import com.mertkahraman.themusicbox.data.api.util.Artists
import com.mertkahraman.themusicbox.data.model.Artist
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// If more MusicBrainz Entity types are added,
// we can generalize lookup methods to a getEntity sort of call.
interface ApiService {

    @GET("artist/{mbid}")
    suspend fun getArtist(
        @Path("mbid") artistMbid: String
    ): Artist

    @GET("artist/")
    suspend fun searchArtists(
        @Query("query") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Artists
}