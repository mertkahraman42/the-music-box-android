package com.mertkahraman.themusicbox.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mertkahraman.themusicbox.data.model.artist.Artist

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

    @Query("SELECT * FROM artists WHERE name LIKE :nameQuery ORDER BY mbScore DESC LIMIT :pageSize OFFSET :offset ")
    suspend fun searchArtists(nameQuery: String, pageSize: Int, offset: Int): List<Artist>
}
