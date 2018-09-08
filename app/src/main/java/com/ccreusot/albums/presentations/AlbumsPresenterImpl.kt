package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.viewmodels.AlbumViewModel

class AlbumsPresenterImpl(private val view: AlbumsView) : AlbumsPresenter {
    override fun presentAlbumsError() {
        view.displayAlbumsError()
    }

    override fun presentEmptyAlbumList() {
        view.displayEmptyAlbumList()
    }

    override fun presentAlbums(albums: List<Album>) {
        val listViewModel : MutableList<AlbumViewModel> = ArrayList()
        albums.forEach {
            listViewModel.add(
                    AlbumViewModel(it)
            )
        }

        view.displayAlbums(listViewModel)
    }
}