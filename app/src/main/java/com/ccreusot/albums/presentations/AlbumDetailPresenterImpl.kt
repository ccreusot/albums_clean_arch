package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.viewmodels.PhotoViewModel

class AlbumDetailPresenterImpl(private val view: AlbumDetailView) : AlbumDetailPresenter {
    override fun presentAlbumIdNegative() {
        view.displayAlbumIdNegative()
    }

    override fun presentAlbumError() {
        view.displayAlbumError()
    }

    override fun presentAlbum(album: Album) {
        val listPhotViewModel : MutableList<PhotoViewModel> = ArrayList()
        album.photos.forEach {
            listPhotViewModel.add(
                PhotoViewModel(it)
            )
        }

        view.displayPhotos(listPhotViewModel)
    }
}