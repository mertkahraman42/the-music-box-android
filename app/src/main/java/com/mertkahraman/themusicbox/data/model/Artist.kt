package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "artists")
class Artist(
    mbid: String,
    @SerializedName("score")
    val mbScore: Int,
    val name: String,
) : MBEntity(mbid) {

    // Overriding equals for testing
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Artist
        if (name != other.name) return false
        if (mbid != other.mbid) return false
        return true
    }
}
