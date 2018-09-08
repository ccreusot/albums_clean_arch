package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album

interface AlbumPresenter {
    fun presentAlbumIdNegative()
    fun presentAlbumError()
    fun presentAlbum(album: Album)
}
