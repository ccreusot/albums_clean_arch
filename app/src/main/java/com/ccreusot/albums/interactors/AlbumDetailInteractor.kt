package com.ccreusot.albums.interactors

import com.ccreusot.albums.presentations.AlbumDetailPresenter
import com.ccreusot.albums.repositories.AlbumDetailRepository

class AlbumDetailInteractor(private val detailPresenter: AlbumDetailPresenter, private val detailRepository: AlbumDetailRepository) {
    fun fetchAlbum(albumId: Long) {
        if (albumId < 0) {
            detailPresenter.presentAlbumIdNegative()
            return
        }

        try {
            detailPresenter.presentAlbum(detailRepository.getAlbum(albumId))
        } catch (e: Exception) {
            detailPresenter.presentAlbumError()
        }
    }
}