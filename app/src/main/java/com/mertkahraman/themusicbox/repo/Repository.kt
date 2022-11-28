package com.mertkahraman.themusicbox.repo

import com.mertkahraman.themusicbox.data.model.Artist

interface Repository {

    suspend fun getArtist(artistMbid: String): Artist?

    suspend fun searchArtists(
        query: String,
        limit: Int = 10,
        offset: Int
    ): List<Artist>?
}