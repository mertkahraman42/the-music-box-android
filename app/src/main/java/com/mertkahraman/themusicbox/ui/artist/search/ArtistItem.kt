package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.artist.Area
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.data.model.artist.LifeSpan
import com.mertkahraman.themusicbox.ui.components.HeadlineText
import com.mertkahraman.themusicbox.ui.components.SupportingText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistItem(
    artist: Artist,
    onItemClick: (Artist) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(artist) }
    ) {
        ListItem(
            text = {
                SupportingText(
                    text = artist.name,
                    color = MaterialTheme.colors.onBackground,
                )
            },
            secondaryText = {
                artist.type?.let {
                    val icon =
                        if (it == "Group")
                            Icons.Default.Group
                        else
                            Icons.Default.Person

                    Row(modifier = Modifier.align(Start)) {
                        Icon(
                            icon,
                            contentDescription = "",
                            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                            modifier = Modifier
                                .padding(top = 3.dp)
                                .size(14.dp)
                        )
                    }
                }
            },
            trailing = {
                ScoreIndicator(artist.mbScore)
            }
        )
    }
}

@Composable
private fun ScoreIndicator(score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            HeadlineText(
                text = score.toString(),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,

            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.TrendingUp,
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.25f),
                modifier = Modifier
                    .size(14.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 300
)
@Composable
fun ArtistItemPreview() {
    val artist = Artist(
        "", 96, "Pink Floyd", "group",
        LifeSpan("1970-10-10", "2019-03-13"),
        Area("England")
    )
    ArtistItem(artist = artist)
}
