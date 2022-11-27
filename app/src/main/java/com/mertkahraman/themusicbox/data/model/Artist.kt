package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity

@Entity(tableName = "artists")
class Artist(
    mbid: String,
    name: String,
) : MBEntity(mbid)
