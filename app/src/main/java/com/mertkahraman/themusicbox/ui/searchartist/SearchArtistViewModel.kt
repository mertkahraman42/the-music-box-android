package com.mertkahraman.themusicbox.ui.searchartist

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.ArtistSource
import kotlinx.coroutines.flow.Flow

class SearchArtistViewModel(
    private val repository: Repository
) : ViewModel() {
    val artistList: Flow<PagingData<Artist>> = Pager(PagingConfig(pageSize = 10)) {
        ArtistSource(repository, "the", 10)
    }.flow
}
