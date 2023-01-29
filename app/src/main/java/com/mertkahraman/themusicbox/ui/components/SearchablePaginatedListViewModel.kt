package com.mertkahraman.themusicbox.ui.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mertkahraman.themusicbox.data.model.MbEntity
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import com.mertkahraman.themusicbox.ui.artist.search.SearchUIEvent
import com.mertkahraman.themusicbox.ui.artist.search.SearchUIState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

open class SearchablePaginatedListViewModel<E : MbEntity>(
    repository: Repository
) : PaginatedListViewModel<ReleaseGroup>(repository) {

    private var _searchUIState = mutableStateOf(SearchUIState())
    val uiState: State<SearchUIState> = _searchUIState

    val searchQuery: String
        get() = _searchUIState.value.searchQuery

    open fun getSearchArgs(): MbEntitySourceArgs? = null

    // Represents the paginated list of E:[MbEntity] data received from repo.
    var mbEntitiesPagingDataFlow: Flow<PagingData<E>>? = null

    // Search related events are received here and the state is updated accordingly.
    fun onEvent(event: SearchUIEvent) {
        viewModelScope.launch {
            val searchQuery = when (event) {
                is SearchUIEvent.SearchValueChanged -> event.searchQueryText
                is SearchUIEvent.SearchCancelled -> ""
            }
            _searchUIState.value = _searchUIState.value.copy(
                searchQuery = searchQuery
            )
            getSearchArgs()?.let { searchArgs ->
                getSearchResultStream(searchArgs)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun getSearchResultStream(args: MbEntitySourceArgs): Flow<PagingData<E>> {
        return Pager(
            PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MbEntitySource(repository, args, DEFAULT_PAGE_SIZE)
            }
        ).flow
            .debounce(TYPEAHEAD_DEBOUNCE_MS)
            .map { pagingData ->
                pagingData.map { it as E }
            }
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val TYPEAHEAD_DEBOUNCE_MS = 300L
    }
}
