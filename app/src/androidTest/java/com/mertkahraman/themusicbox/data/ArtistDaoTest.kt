package com.mertkahraman.themusicbox.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mertkahraman.themusicbox.data.database.TmbDatabase
import com.mertkahraman.themusicbox.data.database.dao.ArtistDao
import com.mertkahraman.themusicbox.data.model.Artist
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArtistDaoTest {
    private lateinit var artistDao: ArtistDao
    private lateinit var db: TmbDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TmbDatabase::class.java
        ).build()
        artistDao = db.artistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun writeArtistAndRead() = runBlocking {
        val newArtist = Artist("1", "new artist")
        artistDao.saveArtist(newArtist)
        val savedArtist = artistDao.getArtist("1")
        assertEquals(savedArtist, newArtist)
    }
}
