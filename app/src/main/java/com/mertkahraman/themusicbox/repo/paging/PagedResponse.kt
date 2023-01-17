package com.mertkahraman.themusicbox.repo.paging

import com.google.gson.annotations.SerializedName
import com.mertkahraman.themusicbox.data.model.MbEntity
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.data.model.artist.Artist

/**
 * Helper class to facilitate consolidation of paginated response for
 * [ArtistsPagedResponse] and [ReleaseGroupsPagedResponse] in a single abstract class.
 * This is primarily used in [MbEntitySource]
 */
abstract class PagedResponse {
    abstract val items: List<MbEntity>
    abstract val totalCount: Int?
    abstract val offset: Int
}

data class ArtistsPagedResponse(
    @SerializedName("artists")
    override val items: List<Artist>,
    @SerializedName("count")
    override val totalCount: Int? = null,
    @SerializedName("offset")
    override val offset: Int
) : PagedResponse()

class ReleaseGroupsPagedResponse(
    @SerializedName("release-groups")
    override val items: List<ReleaseGroup>,
    @SerializedName("release-group-count")
    override val totalCount: Int? = null,
    @SerializedName("release-group-offset")
    override val offset: Int
) : PagedResponse()
