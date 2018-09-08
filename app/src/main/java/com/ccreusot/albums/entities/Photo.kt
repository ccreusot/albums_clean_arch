package com.ccreusot.albums.entities

import com.google.gson.annotations.SerializedName

data class Photo(@SerializedName("id") val id: Long,
                 @SerializedName("title") val title: String,
                 @SerializedName("url") val url: String,
                 @SerializedName("thumbnailUrl") val thumbnailUrl: String)