package com.mertkahraman.themusicbox.data.api

import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.paging.Artists
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroups
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

    @GET("release-group/")
    suspend fun browseReleaseGroupsForArtist(
        @Query("artist") artistMbid: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): ReleaseGroups
}
