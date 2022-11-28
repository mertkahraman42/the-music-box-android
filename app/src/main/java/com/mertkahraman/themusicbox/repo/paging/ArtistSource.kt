package com.mertkahraman.themusicbox.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.repo.Repository

class ArtistSource(
    private val repository: Repository,
    val query: String,
    val pageSize: Int
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val nextPage = params.key ?: 0
            val artistsResponse = repository.searchArtists(query, pageSize, nextPage)

            artistsResponse?.let {
                LoadResult.Page(
                    data = it.artists,
                    prevKey = if (nextPage == 0) null else nextPage - 1,
                    nextKey = it.offset + pageSize
                )
            } ?: run {
                LoadResult.Error(Error("Artists search failed."))
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
}
