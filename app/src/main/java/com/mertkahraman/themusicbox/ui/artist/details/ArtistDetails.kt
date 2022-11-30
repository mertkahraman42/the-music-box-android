package com.mertkahraman.themusicbox.ui.artist.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.ui.components.HeadlineText
import com.mertkahraman.themusicbox.ui.components.SupportingText
import com.mertkahraman.themusicbox.ui.components.TitleText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistDetails(artist: Artist) {
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

                // Detail - Rows
                // Detail info can be:
                // Type: Group / Individual
                // Years: Founded / (if) Dissolved
                // Score: MB Score
                // Bio
                // Genres: Pills view?

                // TODO: Replace Dummy rows with a more dynamic style
                // Dummy info 1
                ListItem(
                    text = {
                        SupportingText(
                            text = "Score",
                            color = MaterialTheme.colors.primary
                        )
                    },
                    secondaryText = {
                        HeadlineText(
                            text = artist.mbScore.toString(),
                            color = MaterialTheme.colors.primary
                        )
                    },
                    icon = {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                    }
                )

                // Dummy info 2
                ListItem(
                    text = {
                        SupportingText(
                            text = "Score",
                            color = MaterialTheme.colors.primary
                        )
                    },
                    secondaryText = {
                        HeadlineText(
                            text = artist.mbScore.toString(),
                            color = MaterialTheme.colors.primary
                        )
                    },
                    icon = {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                    }
                )

                // List of Release groups (albums)
            }
        }
    }
}

@Preview
@Composable
fun ArtistDetailsPreview() {
    Surface(color = MaterialTheme.colors.background) {
        ArtistDetails(
            artist = Artist(
                mbid = "123-123-123-123",
                name = "Pink Floyd",
                mbScore = 95
            )
        )
    }
}
