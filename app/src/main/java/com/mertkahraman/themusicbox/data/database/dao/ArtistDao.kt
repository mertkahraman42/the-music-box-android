package com.mertkahraman.themusicbox.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mertkahraman.themusicbox.data.model.Artist

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(artists: List<Artist>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtist(artist: Artist)

    @Query("SELECT * FROM artists WHERE mbid = :artistId")
    suspend fun getArtist(artistId: String): Artist?

    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<Artist>

    // TODO: Implement pagination
    @Query("SELECT * FROM artists WHERE name LIKE :nameQuery ")
    suspend fun searchArtists(nameQuery: String): List<Artist>?
}
