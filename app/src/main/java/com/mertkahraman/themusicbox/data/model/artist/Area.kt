package com.mertkahraman.themusicbox.data.model.artist

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Area(
    @SerializedName("name")
    val areaName: String?
) : Serializable
