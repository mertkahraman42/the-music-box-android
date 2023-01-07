package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
open class MbEntity(
    @SerializedName("id")
    @PrimaryKey val mbid: String
) {
    // Overriding equals & hashCode for testing
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MbEntity) return false
        if (mbid != other.mbid) return false
        return true
    }

    override fun hashCode(): Int {
        return mbid.hashCode()
    }
}
