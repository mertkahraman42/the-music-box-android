package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity

@Entity(tableName = "artists")
class Artist(
    mbid: String,
    val name: String,
) : MBEntity(mbid)
