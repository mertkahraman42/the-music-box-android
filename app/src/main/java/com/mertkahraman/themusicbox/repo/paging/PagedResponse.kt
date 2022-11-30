package com.mertkahraman.themusicbox.repo.paging

import com.google.gson.annotations.SerializedName
import com.mertkahraman.themusicbox.data.model.Artist
import com.mertkahraman.themusicbox.data.model.ReleaseGroup

open class PagedResponse(
    val offset: Int
)

class Artists(
    val artists: List<Artist>,
    offset: Int
) : PagedResponse(offset)

class ReleaseGroups(
    val releaseGroups: List<ReleaseGroup>,
    offset: Int,
    @SerializedName("release-group-count")
    val count: Int
) : PagedResponse(offset)
