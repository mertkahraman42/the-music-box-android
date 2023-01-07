package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.mertkahraman.themusicbox.data.model.artist.Artist

@Entity(tableName = "release_groups")
class ReleaseGroup(
    mbid: String,
    val title: String,
    @SerializedName("first-release-date")
    val firstReleaseDate: String,
    @SerializedName("primary-type")
    val primaryType: PrimaryType?,

    ) : MbEntity(mbid) {

    enum class PrimaryType(val value: String) {
        @SerializedName("Album")
        ALBUM("Album"),
        @SerializedName("Single")
        SINGLE("Single"),
    }
    // Transient (not marking with annotation as there's a use case for Room)
    var ownerArtistMbid: String? = null

    // Overriding equals for testing
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Artist
        if (title != other.name) return false
        if (mbid != other.mbid) return false
        return true
    }
}
