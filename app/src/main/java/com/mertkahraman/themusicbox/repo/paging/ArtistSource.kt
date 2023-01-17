package com.mertkahraman.themusicbox.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mertkahraman.themusicbox.data.model.artist.Artist
import com.mertkahraman.themusicbox.repo.Repository

// TODO: [Issue#12] Merge PagingSource
class ArtistSource(
    private val repository: Repository,
    var query: String,
    private val pageSize: Int
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val nextPage = params.key ?: 0
            val artistsResponse = repository.searchArtists(query, pageSize, nextPage)

            if (artistsResponse.items.isEmpty())
                LoadResult.Error(NoResultsException("No results retrieved."))
            else
                LoadResult.Page(
                    data = artistsResponse.items,
                    prevKey = if (nextPage == 0) null else nextPage - 1,
                    nextKey = artistsResponse.offset + pageSize
                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    class NoResultsException(message: String) : Exception(message)
    class LoadFailureException(message: String) : Exception(message)
}
