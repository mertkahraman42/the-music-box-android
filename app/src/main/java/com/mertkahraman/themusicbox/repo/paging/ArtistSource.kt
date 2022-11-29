package com.mertkahraman.themusicbox.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository

class ArtistSource(
    private val repository: Repository,
    var query: String,
    private val pageSize: Int
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val nextPage = params.key ?: 0
            val artistsResponse = repository.searchArtists(query, pageSize, nextPage)

            artistsResponse?.let { response ->
                if (response.artists.isEmpty())
                    LoadResult.Error(NoResultsException("No results retrieved."))
                else
                    LoadResult.Page(
                        data = response.artists,
                        prevKey = if (nextPage == 0) null else nextPage - 1,
                        nextKey = response.offset + pageSize
                    )
            } ?: run {
                LoadResult.Error(NoResultsException("No results retrieved."))
            }
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
