package com.mertkahraman.themusicbox.repo

import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.paging.ArtistsPagedResponse
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroupsPagedResponse

interface Repository {

    suspend fun getArtist(artistMbid: String): Artist

    suspend fun searchArtists(
        query: String,
        limit: Int = 10,
        offset: Int
    ): ArtistsPagedResponse

    suspend fun browseReleaseGroups(
        ownerArtistMbid: String,
        limit: Int = 10,
        offset: Int
    ): ReleaseGroupsPagedResponse
}
