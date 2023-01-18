package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
                MbEntitySource(repository, MbEntitySourceArgs.BrowseReleaseGroup(ownerArtistMbid), DEFAULT_PAGE_SIZE)
            }
        ).flow
            .map { pagingData -> pagingData.map { it as ReleaseGroup } }
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
