package com.ccreusot.albums.entities

import com.google.gson.annotations.SerializedName

data class Album(@SerializedName("id") val id: Long,
                 @SerializedName("photos") val photos: MutableList<Photo>)