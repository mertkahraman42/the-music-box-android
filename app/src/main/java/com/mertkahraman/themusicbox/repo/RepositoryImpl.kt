package com.mertkahraman.themusicbox.repo

import android.util.Log
import com.mertkahraman.themusicbox.data.api.ApiService
import com.mertkahraman.themusicbox.data.database.dao.ArtistDao
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.util.TAG

class RepositoryImpl(
    private val apiService: ApiService,
    private val artistDao: ArtistDao,
) : Repository {

    override suspend fun getArtist(artistMbid: String): Artist? {
        val cachedArtist = artistDao.getArtist(artistMbid)
        val apiArtist: Artist
        try {
            apiArtist = apiService.getArtist(artistMbid)
            artistDao.saveArtist(apiArtist)
        } catch (error: Throwable) {
            Log.e(TAG, error.toString())
            return cachedArtist
        }
        return apiArtist
    }

    override suspend fun searchArtists(query: String, limit: Int, offset: Int): List<Artist>? {
        val cachedArtists = artistDao.searchArtists(query)
        var apiArtists: List<Artist>? = null
        try {
            val result = runCatching {
                apiService.searchArtists(query, limit, offset)
            }
            result.onSuccess { pagedResponse ->
                Log.d(TAG, "Page ${pagedResponse.offset} received")
                pagedResponse.artists.map { artist ->
                    Log.d(TAG, "Fetched artist search results:")
                    Log.d(TAG, "Artist Name: ${artist.name}")
                }
                artistDao.saveArtists(pagedResponse.artists)
                apiArtists = pagedResponse.artists
            }.onFailure { error -> throw(error) }
        } catch (error: Throwable) {
            Log.e(TAG, error.toString())
            Log.d(TAG, "Artist fetch failed.")
            apiArtists = cachedArtists
        }
        return apiArtists
    }
}
