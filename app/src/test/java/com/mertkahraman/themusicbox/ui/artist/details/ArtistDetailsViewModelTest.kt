package com.mertkahraman.themusicbox.ui.artist.details

import com.mertkahraman.themusicbox.data.model.artist.Area
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.data.model.artist.LifeSpan
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.rules.MainCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ArtistDetailsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository = mock<Repository>()
    private val fakeArtistMbid = "123"
    private val fakeArtist = Artist(
        fakeArtistMbid,
        1,
        "An artist",
        "Group",
        LifeSpan("1970-10-10", "2010-03-03"),
        Area("London")
    )
    private val viewModel: ArtistDetailsViewModel by lazy {
        ArtistDetailsViewModel(repository, fakeArtistMbid)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testsSaveSessionData() = runTest {
        whenever(repository.getArtist(fakeArtistMbid)).thenReturn(fakeArtist)

        viewModel.fetchArtist()

        assertEquals(fakeArtist, viewModel.artist.value)
    }
}
