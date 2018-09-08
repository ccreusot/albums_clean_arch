package com.ccreusot.albums.presentations

import com.ccreusot.albums.viewmodels.AlbumViewModel

interface AlbumsView {
    fun displayAlbumsError()
    fun displayEmptyAlbumList()
    fun displayAlbums(albumViewModels: List<AlbumViewModel>)
}