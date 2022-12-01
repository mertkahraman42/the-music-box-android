package com.mertkahraman.themusicbox.data.model.artist

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.mertkahraman.themusicbox.data.model.MBEntity

@Entity(tableName = "artists")
class Artist(
    mbid: String,
    @SerializedName("score")
    val mbScore: Int,
    val name: String,
    val type: String?,
    @Embedded
    @SerializedName("life-span")
    val lifeSpan: LifeSpan?
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
