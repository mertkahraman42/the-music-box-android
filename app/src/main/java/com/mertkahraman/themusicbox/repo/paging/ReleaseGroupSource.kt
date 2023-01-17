package com.mertkahraman.themusicbox.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.repo.Repository

// TODO: [Issue#12] Merge PagingSource
class ReleaseGroupSource(
    private val repository: Repository,
    private var ownerArtistMbid: String,
    private val pageSize: Int
) : PagingSource<Int, ReleaseGroup>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReleaseGroup> {
        return try {
            val nextPage = params.key ?: 0
            val rgResponse = repository.browseReleaseGroups(ownerArtistMbid, pageSize, nextPage)

            rgResponse.totalCount?.let { totalItemsCount ->
                if (rgResponse.offset > totalItemsCount && rgResponse.items.isEmpty())
                    return LoadResult.Error(EndOfListException("No more items left to fetch."))
            }

            if (rgResponse.items.isEmpty())
                LoadResult.Error(NoResultsException("No results retrieved."))
            else
                LoadResult.Page(
                    data = rgResponse.items,
                    prevKey = if (nextPage == 0) null else nextPage - 1,
                    nextKey = rgResponse.offset + pageSize
                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReleaseGroup>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    class NoResultsException(message: String) : Exception(message)
    class EndOfListException(message: String) : Exception(message)
}
