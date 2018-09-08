package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.ccreusot.albums.viewmodels.PhotoViewModel
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*

class AlbumDetailPresenterImplTest {

    val view = mock(AlbumDetailView::class.java)
    val presenter = AlbumDetailPresenterImpl(view)

    @Test
    fun test_presentAlbumIdNegative() {
        presenter.presentAlbumIdNegative()

        verify(view).displayAlbumIdNegative()
    }

    @Test
    fun test_presentAlbumError() {
        presenter.presentAlbumError()

        verify(view).displayAlbumError()
    }

    @Test
    fun test_presentAlbum() {
        val album = Album(10,
                mutableListOf(
                        Photo(0, "title", "url", "thumbnail"),
                        Photo(1, "title1", "url", "thumbnail"),
                        Photo(2, "title2", "url", "thumbnail")
                        ))
        presenter.presentAlbum(album)

        verify(view).displayPhotos(Matchers.anyListOf(PhotoViewModel::class.java))
    }
}