package com.mertkahraman.themusicbox.nav

interface SearchArtistDestination {
    val route: String
}

object SearchArtist : SearchArtistDestination {
    override val route: String = "searchArtist"
}
