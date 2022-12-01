package com.mertkahraman.themusicbox.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mertkahraman.themusicbox.data.database.dao.ArtistDao
import com.mertkahraman.themusicbox.data.database.dao.ReleaseGroupDao
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.data.model.artist.LifeSpan

@Database(
    entities = [
        Artist::class,
        ReleaseGroup::class,
        LifeSpan::class
    ],
    version = 1
)
abstract class TmbDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun releaseGroupDao(): ReleaseGroupDao

    companion object {
        fun create(context: Context): TmbDatabase {
            return Room.databaseBuilder(
                context,
                TmbDatabase::class.java,
                "tmb"
            ).build()
        }
    }
}
