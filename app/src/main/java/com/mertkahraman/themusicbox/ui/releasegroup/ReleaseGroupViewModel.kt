package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.paging.PagingData
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import com.mertkahraman.themusicbox.ui.components.PaginatedListViewModel
import kotlinx.coroutines.flow.Flow

class ReleaseGroupViewModel(
    repository: Repository,
    var ownerArtistMbid: String
) : PaginatedListViewModel<ReleaseGroup>(repository) {

    fun browseReleaseGroups(): Flow<PagingData<ReleaseGroup>> {
        val args = MbEntitySourceArgs.BrowseReleaseGroup(ownerArtistMbid)
        return getMbEntitiesStream(args)
    }
}
