package com.mertkahraman.themusicbox.data.model.artist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class LifeSpan(
    @PrimaryKey
    val begin: String,
    val end: String?,
    val ended: Boolean?
) : Serializable {

    override fun toString(): String {
        var s = "Started: $begin"
        if (end != null) s += "\nEnded: $end"
        return s
    }
}
