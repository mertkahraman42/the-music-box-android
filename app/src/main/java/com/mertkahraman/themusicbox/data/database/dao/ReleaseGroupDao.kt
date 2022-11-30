package com.mertkahraman.themusicbox.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.data.model.ReleaseGroup

interface ReleaseGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReleaseGroups(releaseGroups: List<ReleaseGroup>)

    @Query("SELECT * FROM release_group WHERE ownerArtistMbid LIKE :ownerArtistMbid ORDER BY name DESC LIMIT :pageSize OFFSET :offset ")
    suspend fun browseReleaseGroupsForArtist(ownerArtistMbid: String, pageSize: Int, offset: Int): List<Artist>
}
