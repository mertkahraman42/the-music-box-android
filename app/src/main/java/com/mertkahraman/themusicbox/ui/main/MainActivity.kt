package com.mertkahraman.themusicbox.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.ArtistSource
import com.mertkahraman.themusicbox.ui.searchartist.SearchArtist
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val repo: Repository by inject()

    val artistList: Flow<PagingData<Artist>> = Pager(PagingConfig(pageSize = 10)) {
        ArtistSource(repo, "the", 10)
    }.flow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchArtist(artistList)
        }
    }
}
