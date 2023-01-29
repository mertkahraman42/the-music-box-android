package com.mertkahraman.themusicbox.ui.artist.search

import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs
import com.mertkahraman.themusicbox.ui.components.SearchablePaginatedListViewModel

class ArtistSearchViewModel(
    repository: Repository
) : SearchablePaginatedListViewModel<Artist>(repository) {
    var welcomePrompted = false

    override fun getSearchArgs(): MbEntitySourceArgs? {
        return if (searchQuery == "")
            null
        else
            MbEntitySourceArgs.SearchArtist(searchQuery)
    }
}
