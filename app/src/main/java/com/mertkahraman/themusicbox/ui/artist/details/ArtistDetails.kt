package com.mertkahraman.themusicbox.ui.artist.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.artist.getArtistDetailsFor
import com.mertkahraman.themusicbox.ui.components.*
import com.mertkahraman.themusicbox.ui.releasegroup.ReleaseGroupList
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistDetails(
    artistMbid: String,
    viewModel: ArtistDetailsViewModel = getViewModel(
        parameters = { parametersOf(artistMbid) }
    )
) {
    val artist = viewModel.artist.value
    val isLoading = viewModel.isLoading.collectAsState().value

    if (isLoading) {
        FullscreenSpinner()
    } else if (artist == null) {
        ArtistLoadFailure {
            viewModel.fetchArtist()
        }
    } else {
        Column {
            Row {
                LazyColumn(
                    modifier = Modifier.padding(
                        top = 50.dp,
                        start = 20.dp,
                        end = 10.dp
                    )
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Title
                            TitleText(text = artist.name)
                        }
                    }
                    items(
                        getArtistDetailsFor(artist)
                    ) { item ->
                        ListItem(
                            text = {
                                SupportingText(
                                    text = item.title,
                                    color = MaterialTheme.colors.primary
                                )
                            },
                            secondaryText = {
                                item.detail?.let {
                                    HeadlineText(
                                        text = it,
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(end = 8.dp)
                                )
                            }
                        )
                    }
                }
            }
            Row {
                // List of Release groups (albums)
                ReleaseGroupList(artistMbid)
            }
        }
    }
}

@Composable
fun ArtistLoadFailure(
    onRetry: (() -> Unit)?
) {
    Spacer(modifier = Modifier.height(20.dp))
    ErrorListItem(
        modifier = Modifier.fillMaxWidth(),
        errorMessage = "Cannot load artist.",
        onRetry = onRetry
    )
}

@Preview
@Composable
fun ArtistDetailsPreview() {
    Surface(color = MaterialTheme.colors.background) {
        ArtistDetails(
            artistMbid = ""
        )
    }
}
