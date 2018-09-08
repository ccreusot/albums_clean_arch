package com.ccreusot.albums.viewmodels

import com.ccreusot.albums.entities.Album

class AlbumViewModel(private val album: Album) {

    fun getId() : Long {
        return album.id
    }

    fun getPhotoCount() : Int {
        return album.photos.size
    }

    fun getThumbnails() : List<String> {
        val thumbnails : MutableList<String> = ArrayList()
        val size = if (getPhotoCount() > 2) 2 else getPhotoCount() - 1
        for (i in 0..size) {
            thumbnails.add(album.photos[i].thumbnailUrl)
        }
        return thumbnails
    }
}