package com.ccreusot.albums.presentations

import com.ccreusot.albums.viewmodels.PhotoViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class AlbumDetailViewDecorator(private var view: AlbumDetailView?) : AlbumDetailView by view!! {
    override fun displayAlbumIdNegative() {
        launch(UI) {
            view?.displayAlbumIdNegative()
        }
    }

    override fun displayAlbumError() {
        launch(UI) {
            view?.displayAlbumError()
        }
    }

    override fun displayPhotos(photoViewModelList: List<PhotoViewModel>) {
        launch(UI) {
            view?.displayPhotos(photoViewModelList)
        }
    }

    fun destroy() {
        view = null
    }
}