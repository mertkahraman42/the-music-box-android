package com.mertkahraman.themusicbox.repo

import android.util.Log
import com.mertkahraman.themusicbox.data.api.ApiService
import com.mertkahraman.themusicbox.data.database.dao.ArtistDao
import com.mertkahraman.themusicbox.data.database.dao.ReleaseGroupDao
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.paging.ArtistsPagedResponse
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroupsPagedResponse
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

    override suspend fun searchArtists(query: String, limit: Int, offset: Int): ArtistsPagedResponse {
        Log.d(TAG, "Will searchArtists with limit: $limit - offset: $offset")
        var apiArtists: List<Artist> = listOf()
        var artistsCount: Int? = null
        val cachedArtists = artistDao.searchArtists("$query%", limit, offset)
        try {
            val result = runCatching {
                apiService.searchArtists(query, limit, offset)
            }
            result.onSuccess { pagedResponse ->
                Log.d(TAG, "Page ${pagedResponse.offset} received")
                pagedResponse.items.map { artist ->
                    Log.d(TAG, "Fetched artist search results:")
                    Log.d(TAG, "Artist Name: ${artist.name}")
                }
                artistDao.saveArtists(pagedResponse.items)
                apiArtists = pagedResponse.items
                artistsCount = pagedResponse.totalCount
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
        return ArtistsPagedResponse(apiArtists, artistsCount, offset)
    }

    override suspend fun browseReleaseGroups(ownerArtistMbid: String, limit: Int, offset: Int): ReleaseGroupsPagedResponse {
        Log.d(TAG, "Will browseReleaseGroups with limit: $limit - offset: $offset")
        var apiReleaseGroups: List<ReleaseGroup> = listOf()
        var releaseGroupsCount: Int? = null
        val cachedReleaseGroups = releaseGroupDao.browseReleaseGroupsForArtist(ownerArtistMbid, limit, offset)
        try {
            val result = runCatching {
                apiService.browseReleaseGroupsForArtist(ownerArtistMbid, limit, offset)
            }
            result.onSuccess { pagedResponse ->
                Log.d(TAG, "Page ${pagedResponse.offset} received")
                pagedResponse.items.map { releaseGroup ->
                    releaseGroup.ownerArtistMbid = ownerArtistMbid
                    Log.d(TAG, "Fetched releaseGroup browse results:")
                    Log.d(TAG, "ReleaseGroup Name: ${releaseGroup.title}")
                }
                releaseGroupDao.saveReleaseGroups(pagedResponse.items)
                apiReleaseGroups = pagedResponse.items
                releaseGroupsCount = pagedResponse.totalCount
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
        return ReleaseGroupsPagedResponse(apiReleaseGroups, releaseGroupsCount, offset)
    }
}
