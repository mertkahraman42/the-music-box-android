package com.mertkahraman.themusicbox.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mertkahraman.themusicbox.data.model.MbEntity
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class PaginatedListViewModel<E : MbEntity>(
    val repository: Repository
) : ViewModel() {

    fun getMbEntitiesStream(args: MbEntitySourceArgs): Flow<PagingData<E>> {
        return Pager(
            PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MbEntitySource(repository, args, DEFAULT_PAGE_SIZE)
            }
        ).flow
            .map { pagingData -> pagingData.map { it as E } }
            .cachedIn(viewModelScope)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
