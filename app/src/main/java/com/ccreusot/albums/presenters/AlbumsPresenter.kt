package com.ccreusot.albums.presenters

import com.ccreusot.albums.entities.Album

interface AlbumsPresenter {
    fun presentAlbumsError()
    fun presentEmptyAlbumList()
    fun presentAlbums(albums: List<Album>)
}