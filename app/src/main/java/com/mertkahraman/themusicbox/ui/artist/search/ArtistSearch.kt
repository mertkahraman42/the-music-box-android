package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.ui.components.*
import org.koin.androidx.compose.getViewModel

// TODO: [Issue#11] Merge Composables
@Composable
fun ArtistSearch(
    viewModel: ArtistSearchViewModel = getViewModel(),
    onSelectArtist: (Artist) -> Unit = {},
) {
    val localFocus = LocalFocusManager.current
    val searchTextState = viewModel.uiState.value

    val lazyArtistItems: LazyPagingItems<Artist>? = viewModel.mbEntitiesPagingDataFlow?.collectAsLazyPagingItems()

    Column {
        SearchBar(searchTextState) {
            viewModel.onEvent(it)
        }
        LazyColumn {
            if (lazyArtistItems != null) {
                viewModel.welcomePrompted = true // TODO: [Issue#11] Merge Composables
                items(lazyArtistItems) { artist ->
                    ArtistItem(
                        artist = artist!!,
                        onSelectArtist
                    )
                }

                lazyArtistItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { FullscreenSpinner(modifier = Modifier.fillParentMaxSize()) }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { SpinnerListItem() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            localFocus.clearFocus()
                            val refreshError = lazyArtistItems.loadState.refresh as LoadState.Error
                            item {
                                LoadError(refreshError) {
                                    lazyArtistItems.retry()
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            localFocus.clearFocus()
                            val appendError = lazyArtistItems.loadState.append as LoadState.Error
                            item {
                                LoadError(appendError) {
                                    lazyArtistItems.retry()
                                }
                            }
                        }
                    }
                }
            } else if (!viewModel.welcomePrompted) {
                item {
                    WelcomePrompt()
                }
            }
        }
    }
}

@Composable
private fun LoadError(
    appendError: LoadState.Error,
    onRetry: () -> Unit
) {
    when (appendError.error) {
        is MbEntitySource.NoResultsException -> {
            EmptyListIndicator(
                modifier = Modifier.fillMaxSize(),
                query = "this album."
            )
        }
        is MbEntitySource.EndOfListException -> {
            EndOfListIndicator(modifier = Modifier.fillMaxWidth())
        }
        else -> {
            ErrorListItem(
                modifier = Modifier.fillMaxSize(),
                errorMessage = appendError.error.localizedMessage!!,
                onRetry = onRetry
            )
        }
    }
}
