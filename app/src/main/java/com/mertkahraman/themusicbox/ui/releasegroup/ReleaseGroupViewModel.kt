package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.paging.PagingData
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import com.mertkahraman.themusicbox.ui.components.BasePagedMbEntityViewModel
import kotlinx.coroutines.flow.Flow

class ReleaseGroupViewModel(
    private val repository: Repository,
    var ownerArtistMbid: String
) : BasePagedMbEntityViewModel<ReleaseGroup>(repository) {

    fun browseReleaseGroups(): Flow<PagingData<ReleaseGroup>> {
        val args = MbEntitySourceArgs.BrowseReleaseGroup(ownerArtistMbid)
        return getMbEntitiesStream(args)
    }
}
