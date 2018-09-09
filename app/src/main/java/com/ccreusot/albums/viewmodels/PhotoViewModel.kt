package com.ccreusot.albums.viewmodels

import com.ccreusot.albums.entities.Photo

class PhotoViewModel(private val photo: Photo) {

    fun getTitle() : String = photo.title

    fun getPhotoUrl() : String = photo.url

    fun getThumbnail() : String = photo.thumbnailUrl
}