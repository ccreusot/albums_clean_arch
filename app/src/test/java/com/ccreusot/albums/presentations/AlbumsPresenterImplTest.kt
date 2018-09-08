package com.ccreusot.albums.presentations

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.ccreusot.albums.viewmodels.AlbumViewModel
import org.junit.Test
import org.mockito.Matchers.anyListOf
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AlbumsPresenterImplTest {

    val view = mock(AlbumsView::class.java)
    val presenter = AlbumsPresenterImpl(view)

    @Test
    fun test_presentAlbumsError() {
        presenter.presentAlbumsError()

        verify(view).displayAlbumsError()
    }

    @Test
    fun test_presentEmptyAlbumList() {
        presenter.presentEmptyAlbumList()

        verify(view).displayEmptyAlbumList()
    }

    @Test
    fun test_presentAlbums() {
        val albums = listOf(
                Album(0, mutableListOf(Photo(0, "title", "url", "thumbnail"))),
                Album(1, mutableListOf(Photo(0, "title", "url", "thumbnail"))))

        presenter.presentAlbums(albums)

        verify(view).displayAlbums(anyListOf(AlbumViewModel::class.java))
    }
}