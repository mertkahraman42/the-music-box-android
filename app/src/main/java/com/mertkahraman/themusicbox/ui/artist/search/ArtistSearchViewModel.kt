package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.ArtistSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ArtistSearchViewModel(
    private val repository: Repository
) : ViewModel() {

    // TODO: Implement Debounce on typeahead search
    private var _searchUIState = mutableStateOf(SearchUIState())
    val uiState: State<SearchUIState> = _searchUIState

    fun onEvent(event: SearchUIEvent) {
        viewModelScope.launch {
            val searchQuery = when (event) {
                is SearchUIEvent.SearchValueChanged -> event.searchQueryText
                is SearchUIEvent.SearchCancelled -> ""
            }
            _searchUIState.value = _searchUIState.value.copy(
                searchQuery = searchQuery
            )

            if (searchQuery != "")
                getSearchResultStream(searchQuery)
        }
    }

    fun getSearchResultStream(query: String): Flow<PagingData<Artist>>? {
        return if (query == "") {
            null
        } else {
            Pager(
                PagingConfig(
                    pageSize = DEFAULT_PAGE_SIZE
                ),
                pagingSourceFactory = {
                    ArtistSource(repository, query, DEFAULT_PAGE_SIZE)
                }
            ).flow.cachedIn(viewModelScope)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
