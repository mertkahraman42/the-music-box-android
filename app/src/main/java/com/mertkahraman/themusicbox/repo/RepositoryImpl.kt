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
}
