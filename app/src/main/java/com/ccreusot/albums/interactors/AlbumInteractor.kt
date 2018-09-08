package com.ccreusot.albums.interactors

import com.ccreusot.albums.presentations.AlbumPresenter
import com.ccreusot.albums.repositories.AlbumRepository

class AlbumInteractor(private val presenter: AlbumPresenter, private val repository: AlbumRepository) {
    fun fetchAlbum(albumId: Long) {
        if (albumId < 0) {
            presenter.presentAlbumIdNegative()
            return
        }

        try {
            presenter.presentAlbum(repository.getAlbum(albumId))
        } catch (e: Exception) {
            presenter.presentAlbumError()
        }
    }
}