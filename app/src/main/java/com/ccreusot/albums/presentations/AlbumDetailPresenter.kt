package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album

interface AlbumDetailPresenter {
    fun presentAlbumIdNegative()
    fun presentAlbumError()
    fun presentAlbum(album: Album)
}
