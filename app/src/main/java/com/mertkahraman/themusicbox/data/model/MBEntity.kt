package com.mertkahraman.themusicbox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class MBEntity(
    @PrimaryKey val mbid: String
) {

    // Overriding equals & hashCode for testing
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MBEntity) return false
        if (mbid != other.mbid) return false
        return true
    }

    override fun hashCode(): Int {
        return mbid.hashCode()
    }
}
