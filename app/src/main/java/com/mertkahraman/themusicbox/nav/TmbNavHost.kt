package com.mertkahraman.themusicbox.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mertkahraman.themusicbox.nav.ArtistsDestination.Companion.KEY_MBID
import com.mertkahraman.themusicbox.ui.artist.details.ArtistDetails
import com.mertkahraman.themusicbox.ui.artist.search.ArtistSearch

@Composable
fun TmbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Artists.route,
        modifier = modifier
    ) {
        // Artist Search
        composable(
            route = Artists.route,
        ) {
            ArtistSearch(
                onSelectArtist = { artist ->
                    navController.navigateToArtistDetails(artist.mbid)
                }
            )
        }
        // Artist Details
        composable(
            route = ArtistDetails.routeWithArgs(),
            arguments = ArtistDetails.arguments,
        ) { navBackStackEntry ->
            val artistMbid = navBackStackEntry.arguments?.getString(KEY_MBID)
            ArtistDetails(
                artistMbid = artistMbid
            )
        }
    }
}

private fun NavHostController.navigateToArtistDetails(artistMbid: String) {
    navigate("${ArtistDetails.route}/$artistMbid") {
        restoreState = true
    }
}
