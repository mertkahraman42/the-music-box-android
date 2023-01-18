package com.mertkahraman.themusicbox.repo.paging

sealed class MbEntitySourceArgs {
    data class SearchArtist(val query: String) : MbEntitySourceArgs()
    data class BrowseReleaseGroup(val ownerArtistMbid: String) : MbEntitySourceArgs()
}
