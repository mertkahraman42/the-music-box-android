package com.mertkahraman.themusicbox.ui.artist.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.util.TAG
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
            Log.d(TAG, "Attempting to fetch artist details with $artistMbid")
            result.onSuccess { fetchedArtist ->
                Log.d(TAG, "Artist retrieved: ${fetchedArtist.name}")
                artist.value = fetchedArtist
                _isLoading.update { false }
            }.onFailure { error ->
                Log.e(TAG, error.toString())
                // TODO: Implement error flow
                _isLoading.update { false }
            }
        }
    }
}
