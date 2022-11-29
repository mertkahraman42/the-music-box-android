package com.mertkahraman.themusicbox.ui.searchartist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.paging.ArtistSource
import com.mertkahraman.themusicbox.ui.components.EmptyListIndicator
import com.mertkahraman.themusicbox.ui.components.ErrorListItem
import com.mertkahraman.themusicbox.ui.components.FullscreenSpinner
import com.mertkahraman.themusicbox.ui.components.SpinnerListItem
import org.koin.androidx.compose.getViewModel

// This assignment project has a narrow scope and we won't be using this page to search anything else,
// However, this could be easily extended to something like SearchEntity to search all sorts of MB entities.
@Composable
fun SearchArtist(
    viewModel: SearchArtistViewModel = getViewModel()
) {
    val searchTextState = viewModel.uiState.value
    val lazyArtistItems: LazyPagingItems<Artist>? =
        viewModel.getSearchResultStream(searchTextState.searchQuery)?.collectAsLazyPagingItems()

    Column {
        SearchView(searchTextState) {
            viewModel.onEvent(it)
        }
        LazyColumn {
            if (lazyArtistItems != null) {
                items(lazyArtistItems) { artist ->
                    ArtistItem(artist = artist!!)
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
                            val loadStateError = lazyArtistItems.loadState.refresh as LoadState.Error
                            when (loadStateError.error) {
                                is ArtistSource.NoResultsException -> {
                                    item {
                                        EmptyListIndicator(
                                            modifier = Modifier.fillParentMaxSize(),
                                            query = searchTextState.searchQuery
                                        )
                                    }
                                }
                                else -> {
                                    item {
                                        ErrorListItem(
                                            modifier = Modifier.fillParentMaxSize(),
                                            errorMessage = loadStateError.error.localizedMessage!!,
                                            onRetry = { retry() }
                                        )
                                    }
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyArtistItems.loadState.append as LoadState.Error
                            item {
                                ErrorListItem(
                                    errorMessage = e.error.localizedMessage!!,
                                    onRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}