package com.mertkahraman.themusicbox.data.model.artist

import androidx.room.Entity
import java.io.Serializable

@Entity
class LifeSpan(
    val begin: String?,
    val end: String?,
) : Serializable {

    override fun toString(): String {
        var s = ""
        if (begin != null) s += "Started: $begin"
        if (end != null) s += "\nEnded: $end"
        return s
    }
}
