package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class MBEntity(
    @PrimaryKey val mbid: String
)
