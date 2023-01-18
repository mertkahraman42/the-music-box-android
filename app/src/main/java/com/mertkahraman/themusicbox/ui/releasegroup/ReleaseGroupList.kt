package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.paging.MbEntitySource
import com.mertkahraman.themusicbox.ui.components.*
import org.koin.androidx.compose.getViewModel

// TODO: [Issue#11] Merge Composables
@Composable
fun ReleaseGroupList(
    ownerArtistMbid: String,
    viewModel: ReleaseGroupViewModel = getViewModel(),
    onSelectReleaseGroup: (ReleaseGroup) -> Unit = {},
) {
    val lazyReleaseGroupItems: LazyPagingItems<ReleaseGroup>? =
        viewModel.getReleaseGroupsForArtistStream(ownerArtistMbid)?.collectAsLazyPagingItems()

    Column {
        LazyColumn {
            if (lazyReleaseGroupItems != null) {
                items(lazyReleaseGroupItems) { releaseGroup ->
                    ReleaseGroupItem(
                        releaseGroup = releaseGroup!!,
                        onSelectReleaseGroup
                    )
                    Divider() // TODO: [Issue#11] Merge Composables
                }

                lazyReleaseGroupItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { FullscreenSpinner(modifier = Modifier.fillParentMaxSize()) }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { SpinnerListItem() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val refreshError = lazyReleaseGroupItems.loadState.refresh as LoadState.Error
                            when (refreshError.error) {
                                is MbEntitySource.NoResultsException -> {
                                    item {
                                        EmptyListIndicator( // TODO: [Issue#11] Merge Composables
                                            modifier = Modifier.fillParentMaxSize(),
                                            query = "this album."
                                        )
                                    }
                                }
                                is MbEntitySource.EndOfListException -> { // TODO: [Issue#11] Merge Composables
                                    item {
                                        EndOfListIndicator(modifier = Modifier.fillMaxWidth())
                                    }
                                }
                                else -> {
                                    item {
                                        ErrorListItem(
                                            modifier = Modifier.fillParentMaxSize(),
                                            errorMessage = refreshError.error.localizedMessage!!,
                                            onRetry = { retry() }
                                        )
                                    }
                                }
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val appendError = lazyReleaseGroupItems.loadState.append as LoadState.Error
                            when (appendError.error) {
                                is MbEntitySource.NoResultsException -> {
                                    item {
                                        EmptyListIndicator(
                                            modifier = Modifier.fillParentMaxSize(),
                                            query = "this album."
                                        )
                                    }
                                }
                                is MbEntitySource.EndOfListException -> {
                                    item {
                                        EndOfListIndicator(modifier = Modifier.fillMaxWidth())
                                    }
                                }
                                else -> {
                                    item {
                                        ErrorListItem(
                                            modifier = Modifier.fillParentMaxSize(),
                                            errorMessage = appendError.error.localizedMessage!!,
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
    }
}
