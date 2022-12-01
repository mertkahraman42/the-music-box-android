package com.mertkahraman.themusicbox.repo.paging

import com.google.gson.annotations.SerializedName
import com.mertkahraman.themusicbox.data.model.ReleaseGroup
import com.mertkahraman.themusicbox.data.model.artist.Artist

open class PagedResponse(
    val offset: Int
)

class Artists(
    val artists: List<Artist>,
    offset: Int
) : PagedResponse(offset)

class ReleaseGroups(
    @SerializedName("release-groups")
    val releaseGroups: List<ReleaseGroup>,
    @SerializedName("release-group-offset")
    val releaseGroupOffset: Int,
    @SerializedName("release-group-count")
    val count: Int? = null
) : PagedResponse(offset = releaseGroupOffset)
