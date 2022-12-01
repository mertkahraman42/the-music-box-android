package com.mertkahraman.themusicbox.ui.artist.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.artist.getArtistDetailsFor
import com.mertkahraman.themusicbox.ui.components.ErrorListItem
import com.mertkahraman.themusicbox.ui.components.FullscreenSpinner
import com.mertkahraman.themusicbox.ui.components.TitleText
import com.mertkahraman.themusicbox.ui.releasegroup.ReleaseGroupList
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

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
        Column(
            modifier = Modifier.padding(
                top = 50.dp,
                start = 20.dp,
                end = 10.dp
            )
        ) {
            Row {
                TitleText(text = artist.name)
            }

            ArtistBio(getArtistDetailsFor(artist))

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
