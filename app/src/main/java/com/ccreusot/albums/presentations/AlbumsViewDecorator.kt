package com.ccreusot.albums.presentations

import com.ccreusot.albums.viewmodels.AlbumViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * This will be only use to prevent crash if the view is destroy during the execution of
 * code in another thread. Also it will execute all the view in the main thread.
 */
class AlbumsViewDecorator(private var view: AlbumsView?) : AlbumsView by view!! {

    override fun displayAlbumsError() {
        launch(UI) {
            view?.displayAlbumsError()
        }
    }

    override fun displayEmptyAlbumList() {
        launch(UI) {
            view?.displayEmptyAlbumList()
        }
    }

    override fun displayAlbums(albumViewModels: List<AlbumViewModel>) {
        launch(UI) {
            view?.displayAlbums(albumViewModels)
        }
    }

    fun destroy() {
        view = null
    }
}