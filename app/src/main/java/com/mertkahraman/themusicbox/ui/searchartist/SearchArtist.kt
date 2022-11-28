package com.mertkahraman.themusicbox.ui.searchartist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mertkahraman.themusicbox.data.model.Artist
import kotlinx.coroutines.flow.Flow

// This assignment project has a narrow scope and we won't be using this page to search anything else,
// However, this could be easily extended to something like SearchEntity to search all sorts of MB entities.
@Composable
fun SearchArtist(artists: Flow<PagingData<Artist>>) {
    val lazyArtistItems: LazyPagingItems<Artist> = artists.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyArtistItems) { artist ->
            ArtistItem(artist = artist!!)
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    Column {
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
            Text(text = artist.mbid)
        }
    }
}
