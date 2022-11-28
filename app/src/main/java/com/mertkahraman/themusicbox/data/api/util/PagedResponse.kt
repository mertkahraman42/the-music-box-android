package com.mertkahraman.themusicbox.data.api.util

import com.mertkahraman.themusicbox.data.model.Artist

open class PagedResponse(
    val offset: Int
)

class Artists(
    val artists: List<Artist>,
    offset: Int
) : PagedResponse(offset)
