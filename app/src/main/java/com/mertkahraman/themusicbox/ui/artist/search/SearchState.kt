package com.mertkahraman.themusicbox.ui.artist.search

// Contains all Search and Search Focus related objects

sealed class SearchUIEvent {
    data class SearchValueChanged(val searchQueryText: String) : SearchUIEvent()
    object SearchCancelled : SearchUIEvent()
}

data class SearchUIState(
    val searchQuery: String = "",
    val isSearchFocus: Boolean = false,
)
