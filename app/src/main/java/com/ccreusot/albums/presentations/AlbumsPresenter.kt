package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album

interface AlbumsPresenter {
    fun presentAlbumsError()
    fun presentEmptyAlbumList()
    fun presentAlbums(albums: List<Album>)
}