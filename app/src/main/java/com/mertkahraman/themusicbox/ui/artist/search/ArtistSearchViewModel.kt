package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mertkahraman.themusicbox.data.model.artist.Artist
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

    private val _searchQuery: String
        get() = _searchUIState.value.searchQuery

    var welcomePrompted = false

    // Represents the paginated list of [Artist] data received from repo.
    var artistsPagingDataFlow: Flow<PagingData<Artist>>? = getSearchResultStream()

    // Search related events are received here and the state is updated accordingly.
    fun onEvent(event: SearchUIEvent) {
        // TODO: Add Offer Channel flow here so we can flatmap latest and debounce the events.
        viewModelScope.launch {
            val searchQuery = when (event) {
                is SearchUIEvent.SearchValueChanged -> event.searchQueryText
                is SearchUIEvent.SearchCancelled -> ""
            }
            _searchUIState.value = _searchUIState.value.copy(
                searchQuery = searchQuery
            )
            artistsPagingDataFlow = getSearchResultStream()
        }
    }

    // Each time our search query changes, we define a new Flow,
    // using a new PagingData with the new search query.
    private fun getSearchResultStream(): Flow<PagingData<Artist>>? {
        return if (_searchQuery == "") {
            null
        } else {
            Pager(
                PagingConfig(
                    pageSize = DEFAULT_PAGE_SIZE
                ),
                pagingSourceFactory = {
                    ArtistSource(repository, _searchQuery, DEFAULT_PAGE_SIZE)
                }
            ).flow.cachedIn(viewModelScope)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
