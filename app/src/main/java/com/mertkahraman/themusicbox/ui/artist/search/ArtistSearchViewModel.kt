package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ArtistSearchViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _searchUIState = mutableStateOf(SearchUIState())
    val uiState: State<SearchUIState> = _searchUIState

    private val _searchQuery: String
        get() = _searchUIState.value.searchQuery

    var welcomePrompted = false

    // Represents the paginated list of [Artist] data received from repo.
    var artistsPagingDataFlow: Flow<PagingData<Artist>>? = null

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
            artistsPagingDataFlow = if (searchQuery == "") null else getSearchResultStream()
        }
    }

    // TODO: [Issue#2] Merge VMs
    // Each time our search query changes, we define a new Flow,
    // using a new PagingData with the new search query.
    @OptIn(FlowPreview::class)
    private fun getSearchResultStream(): Flow<PagingData<Artist>> {
        return Pager(
            PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                MbEntitySource(repository, MbEntitySourceArgs.SearchArtist(_searchQuery), DEFAULT_PAGE_SIZE)
            }
        ).flow
            .debounce(TYPEAHEAD_DEBOUNCE_MS)
            .map { pagingData -> pagingData.map { it as Artist } }
            .cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
        private const val TYPEAHEAD_DEBOUNCE_MS = 300L
    }
}
