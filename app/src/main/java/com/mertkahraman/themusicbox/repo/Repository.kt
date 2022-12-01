package com.mertkahraman.themusicbox.repo

import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.paging.Artists
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroups

interface Repository {

    suspend fun getArtist(artistMbid: String): Artist

    suspend fun searchArtists(
        query: String,
        limit: Int = 10,
        offset: Int
    ): Artists

    suspend fun browseReleaseGroups(
        ownerArtistMbid: String,
        limit: Int = 10,
        offset: Int
    ): ReleaseGroups
}
