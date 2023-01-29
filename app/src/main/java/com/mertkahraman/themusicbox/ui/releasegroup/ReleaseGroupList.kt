package com.mertkahraman.themusicbox.ui.releasegroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import org.koin.core.parameter.parametersOf

// TODO: [Issue#11] Merge Composables
@Composable
fun ReleaseGroupList(
    ownerArtistMbid: String,
    viewModel: ReleaseGroupViewModel = getViewModel(
        parameters = { parametersOf(ownerArtistMbid) }
    ),
    onSelectReleaseGroup: (ReleaseGroup) -> Unit = {},
) {
    val lazyReleaseGroupItems: LazyPagingItems<ReleaseGroup> =
        viewModel.browseReleaseGroups().collectAsLazyPagingItems()

    Column {
        LazyColumn {
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
                        item {
                            LoadError(refreshError) {
                                lazyReleaseGroupItems.retry()
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val appendError = lazyReleaseGroupItems.loadState.append as LoadState.Error
                        item {
                            LoadError(appendError) {
                                lazyReleaseGroupItems.retry()
                            }
                        }
                    }
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
