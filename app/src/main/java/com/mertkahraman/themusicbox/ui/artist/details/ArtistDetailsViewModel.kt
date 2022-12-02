package com.mertkahraman.themusicbox.ui.artist.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtistDetailsViewModel(
    private val repository: Repository,
    private val artistMbid: String
) : ViewModel() {

    val artist: MutableState<Artist?> = mutableStateOf(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchArtist()
    }

    fun fetchArtist() {
        viewModelScope.launch {
            _isLoading.update { true }

            val result = runCatching {
                repository.getArtist(artistMbid)
            }
            result.onSuccess { fetchedArtist ->
                artist.value = fetchedArtist
                _isLoading.update { false }
            }.onFailure { error ->
                // TODO: Implement error flow
                _isLoading.update { false }
            }
        }
    }
}
