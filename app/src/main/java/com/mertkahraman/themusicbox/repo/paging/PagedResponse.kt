package com.mertkahraman.themusicbox.repo.paging

import com.mertkahraman.themusicbox.data.model.Artist

open class PagedResponse(
    val offset: Int
)

class Artists(
    val artists: List<Artist>,
    offset: Int
) : PagedResponse(offset)
