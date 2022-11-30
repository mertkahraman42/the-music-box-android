package com.mertkahraman.themusicbox.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mertkahraman.themusicbox.ui.searchartist.SearchArtist
import com.mertkahraman.themusicbox.util.TAG

@Composable
fun TmbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SearchArtist.route,
        modifier = modifier
    ) {
        // Search
        composable(
            route = SearchArtist.route,
        ) {
            SearchArtist(
                onSelectArtist = { artist ->
                    navController.navigateToArtistDetails(artist.mbid)
                }
            )
        }
    }
}

private fun NavHostController.navigateToArtistDetails(artistMbid: String) {
    Log.d(TAG, "Will navigate to Artist details with id: $artistMbid")
    // TODO: Implement click thru nav to artist details
}
