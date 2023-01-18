package com.mertkahraman.themusicbox.repo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mertkahraman.themusicbox.data.model.MbEntity
import com.mertkahraman.themusicbox.repo.Repository
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs.BrowseReleaseGroup
import com.mertkahraman.themusicbox.repo.paging.MbEntitySourceArgs.SearchArtist
/**
 * Main PagingSource class used to request all sorts of [MbEntity] objects.
 * Casting the objects to respective entities should be done in the VMs.
 * [args] is used as a switch param to differentiate which request
 * this [PagingSource] should execute.
 */
class MbEntitySource(
    private val repository: Repository,
    private val args: MbEntitySourceArgs, // TODO: Create Args (enum?) class implementation
    private val pageSize: Int
) : PagingSource<Int, MbEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MbEntity> {
        val nextPage = params.key ?: 0
        try {
            val pagedResponse: PagedResponse = when (args) {
                is SearchArtist ->
                    repository.searchArtists(args.query, pageSize, nextPage)
                is BrowseReleaseGroup ->
                    repository.browseReleaseGroups(args.ownerArtistMbid, pageSize, nextPage)
            }

            pagedResponse.totalCount?.let { totalCount ->
                if (pagedResponse.offset > totalCount && pagedResponse.items.isEmpty())
                    return LoadResult.Error(EndOfListException("No more items left to fetch."))
            }

            if (pagedResponse.items.isEmpty())
                return LoadResult.Error(NoResultsException("No results retrieved."))

            return LoadResult.Page(
                data = pagedResponse.items,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = pagedResponse.offset + pageSize
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MbEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    class NoResultsException(message: String) : Exception(message)
    class LoadFailureException(message: String) : Exception(message)
    class EndOfListException(message: String) : Exception(message)
}
