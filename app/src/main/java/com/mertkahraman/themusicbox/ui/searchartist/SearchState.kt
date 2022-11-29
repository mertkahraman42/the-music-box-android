package com.mertkahraman.themusicbox.ui.searchartist

// Contains all Search and Search Focus related objects

sealed class SearchUIEvent {
    data class SearchValueChanged(val searchQueryText: String) : SearchUIEvent()
    data class SearchUnfocused(val searchQueryText: String) : SearchUIEvent()
    object SearchCleared : SearchUIEvent()
}

data class SearchUIState(
    val searchQuery: String = "",
    val isSearchFocus: Boolean = false,
)
