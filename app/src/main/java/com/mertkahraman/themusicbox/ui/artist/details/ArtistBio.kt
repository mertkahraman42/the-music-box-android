package com.mertkahraman.themusicbox.ui.artist.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.artist.*
import com.mertkahraman.themusicbox.ui.components.HeadlineText

@Composable
fun ArtistBio(artistDetails: List<ArtistDetail>) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(artistDetails) { artistDetail ->
            ArtistBioItem(artistDetail)
        }
    }
}

@Composable
fun ArtistBioItem(artistDetail: ArtistDetail) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                artistDetail.icon,
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.75f),
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            HeadlineText(
                text = artistDetail.detail,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center,

            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
fun ArtistBioPreview() {
    val artist = Artist(
        "", 96, "Pink Floyd", "group",
        LifeSpan("1970-10-10", "2019-03-13"),
        Area("England")
    )
    val details = getArtistDetailsFor(artist)
    ArtistBio(artistDetails = details)
}
