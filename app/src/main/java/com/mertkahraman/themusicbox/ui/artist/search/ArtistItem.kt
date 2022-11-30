package com.mertkahraman.themusicbox.ui.artist.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mertkahraman.themusicbox.data.model.Artist

// TODO: Revise UI
@Composable
fun ArtistItem(
    artist: Artist,
    onItemClick: (Artist) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick(artist) }
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = artist.name)
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = artist.mbScore.toString())
        }
    }
}
