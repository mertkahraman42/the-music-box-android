package com.mertkahraman.themusicbox.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mertkahraman.themusicbox.data.model.ReleaseGroup

@Dao
interface ReleaseGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReleaseGroups(releaseGroups: List<ReleaseGroup>)

    @Query("SELECT * FROM release_groups WHERE ownerArtistMbid LIKE :ownerArtistMbid ORDER BY title DESC LIMIT :pageSize OFFSET :offset ")
    suspend fun browseReleaseGroupsForArtist(ownerArtistMbid: String, pageSize: Int, offset: Int): List<ReleaseGroup>
}
