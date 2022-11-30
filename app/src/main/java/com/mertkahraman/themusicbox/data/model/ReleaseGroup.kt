package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "release_group")
class ReleaseGroup(
    mbid: String,
    val name: String,
    @SerializedName("first-release-date")
    val firstReleaseDate: String,
    @Transient var ownerArtistMbid: String
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
