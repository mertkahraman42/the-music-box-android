package com.mertkahraman.themusicbox.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mertkahraman.themusicbox.nav.ArtistsDestination.Companion.KEY_MBID

interface ArtistsDestination {
    val route: String

    companion object {
        const val KEY_MBID = "mbid"
    }
}

object Artists : ArtistsDestination {
    override val route: String = "artistSearch"
}

object ArtistDetails : ArtistsDestination {
    override val route: String = "artistDetails"

    val arguments = listOf(
        navArgument(KEY_MBID) { type = NavType.StringType }
    )

    fun routeWithArgs() =
        "$route/{$KEY_MBID}"
}
