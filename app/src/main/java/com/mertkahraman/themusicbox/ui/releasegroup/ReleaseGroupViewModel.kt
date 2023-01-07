package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.ReleaseGroupSource
import kotlinx.coroutines.flow.Flow

class ReleaseGroupViewModel(
    private val repository: Repository
) : ViewModel() {

    // TODO: [Issue#2] Merge VMs
    fun getReleaseGroupsForArtistStream(ownerArtistMbid: String): Flow<PagingData<ReleaseGroup>>? {
        return Pager(
            PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                ReleaseGroupSource(repository, ownerArtistMbid, DEFAULT_PAGE_SIZE)
            }
        ).flow.cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
