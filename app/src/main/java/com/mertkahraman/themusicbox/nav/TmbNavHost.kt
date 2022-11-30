package com.mertkahraman.themusicbox.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mertkahraman.themusicbox.ui.searchartist.SearchArtist

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
            // TODO: Add onClickArtist navigation to Artist Details.
            SearchArtist()
        }
    }
}
