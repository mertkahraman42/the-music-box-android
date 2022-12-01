package com.mertkahraman.themusicbox.repo

import android.util.Log
import com.mertkahraman.themusicbox.data.api.ApiService
import com.mertkahraman.themusicbox.data.database.dao.ArtistDao
import com.mertkahraman.themusicbox.data.database.dao.ReleaseGroupDao
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.paging.Artists
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroups
import com.mertkahraman.themusicbox.util.TAG

class RepositoryImpl(
    private val apiService: ApiService,
    private val artistDao: ArtistDao,
    private val releaseGroupDao: ReleaseGroupDao,
) : Repository {

    // TODO: Rename api prefix variables

    override suspend fun getArtist(artistMbid: String): Artist {
        val cachedArtist = artistDao.getArtist(artistMbid)
        var apiArtist: Artist
        try {
            apiArtist = apiService.getArtist(artistMbid)
            artistDao.saveArtist(apiArtist)
        } catch (error: Throwable) {
            Log.e(TAG, error.toString())
            if (cachedArtist != null) {
                apiArtist = cachedArtist
            } else {
                throw(error)
            }
        }
        return apiArtist
    }

    override suspend fun searchArtists(query: String, limit: Int, offset: Int): Artists {
        Log.d(TAG, "Will searchArtists with limit: $limit - offset: $offset")
        var apiArtists: List<Artist> = listOf()
        val cachedArtists = artistDao.searchArtists("$query%", limit, offset)
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
            }.onFailure { error ->
                Log.d(TAG, "Artist fetch failed with error: ${error.localizedMessage}")
                throw(error)
            }
        } catch (error: Throwable) {
            Log.d(TAG, "Artist fetch failed with error: ${error.localizedMessage}")
            if (cachedArtists.isNotEmpty()) {
                apiArtists = cachedArtists
            } else {
                throw(error)
            }
        }
        return Artists(apiArtists, offset)
    }

    override suspend fun browseReleaseGroups(ownerArtistMbid: String, limit: Int, offset: Int): ReleaseGroups {
        Log.d(TAG, "Will browseReleaseGroups with limit: $limit - offset: $offset")
        var apiReleaseGroups: List<ReleaseGroup> = listOf()
        var numberOfReleaseGroups: Int? = null
        val cachedReleaseGroups = releaseGroupDao.browseReleaseGroupsForArtist(ownerArtistMbid, limit, offset)
        try {
            val result = runCatching {
                apiService.browseReleaseGroupsForArtist(ownerArtistMbid, "album|ep", limit, offset)
            }
            result.onSuccess { pagedResponse ->
                Log.d(TAG, "Page ${pagedResponse.offset} received")
                pagedResponse.releaseGroups.map { releaseGroup ->
                    releaseGroup.ownerArtistMbid = ownerArtistMbid
                    Log.d(TAG, "Fetched releaseGroup browse results:")
                    Log.d(TAG, "ReleaseGroup Name: ${releaseGroup.title}")
                }
                releaseGroupDao.saveReleaseGroups(pagedResponse.releaseGroups)
                apiReleaseGroups = pagedResponse.releaseGroups
                numberOfReleaseGroups = pagedResponse.count
            }.onFailure { error ->
                Log.d(TAG, "ReleaseGroup fetch failed with error: ${error.localizedMessage}")
                throw(error)
            }
        } catch (error: Throwable) {
            Log.d(TAG, "ReleaseGroup fetch failed with error: ${error.localizedMessage}")
            if (cachedReleaseGroups.isNotEmpty()) {
                apiReleaseGroups = cachedReleaseGroups
            } else {
                throw(error)
            }
        }
        return ReleaseGroups(apiReleaseGroups, offset, numberOfReleaseGroups)
    }
}
