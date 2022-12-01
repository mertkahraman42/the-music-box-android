package com.mertkahraman.themusicbox.data.model.artist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.*

data class ArtistDetail(
    val icon: ImageVector,
    val title: String?,
    val detail: String
)

fun getArtistDetailsFor(artist: Artist): List<ArtistDetail> {
    val artistDetails = mutableListOf<ArtistDetail>()

    // Type: Group / Person
    artist.type?.let {
        val icon =
            if (it == "Group")
                Icons.Default.Group
            else
                Icons.Default.Person

        artistDetails.add(
            ArtistDetail(
                icon,
                null,
                it.capitalize(Locale.ROOT)
            )
        )
    }
    // Life Span
    artist.lifeSpan?.let {
        val icon = Icons.Default.CalendarMonth

        artistDetails.add(
            ArtistDetail(
                icon,
                "Years",
                it.toString()
            )
        )
    }
    // Area
    artist.area?.areaName?.let { areaName ->
        val icon = Icons.Default.Map

        artistDetails.add(
            ArtistDetail(
                icon,
                "Area",
                areaName
            )
        )
    }
    return artistDetails
}
