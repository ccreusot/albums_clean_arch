package com.ccreusot.albums.interactors

import com.ccreusot.albums.presenters.AlbumsPresenter
import com.ccreusot.albums.repositories.AlbumsRepository

class AlbumsInteractor(private val presenter: AlbumsPresenter,
                       private val repository: AlbumsRepository) {
    fun fetchAlbums() {
        try {
            val albums = repository.getAlbums()
            if (albums.isEmpty()) {
                presenter.presentEmptyAlbumList()
            } else {
                presenter.presentAlbums(albums)
            }
        } catch (e: Exception) {
            presenter.presentAlbumsError()
        }
    }

}