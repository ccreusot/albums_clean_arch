package com.ccreusot.albums.interactors

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.ccreusot.albums.presentations.AlbumDetailPresenter
import com.ccreusot.albums.repositories.AlbumDetailRepository
import org.junit.Test
import org.mockito.Mockito.*

class AlbumDetailInteractorTest {
    val repository = mock(AlbumDetailRepository::class.java)
    val presenter = mock(AlbumDetailPresenter::class.java)
    val interactor = AlbumDetailInteractor(presenter, repository)

    @Test
    fun test_fetchAlbum_withIdNegative() {
        val albumId = -1L

        interactor.fetchAlbum(albumId)

        verifyZeroInteractions(repository)
        verify(presenter).presentAlbumIdNegative()
    }

    @Test
    fun test_fetchAlbum_withException() {
        val albumId = 1L

        doThrow(Exception::class.java).`when`(repository).getAlbum(albumId)

        interactor.fetchAlbum(albumId)

        verify(repository).getAlbum(albumId)
        verify(presenter).presentAlbumError()
    }


    @Test
    fun test_fetchAlbum() {
        val albumId = 1L
        val album = Album(albumId, mutableListOf(
                Photo(0, "title", "url", "thumbnail")
        ))

        `when`(repository.getAlbum(albumId)).thenReturn(
                album
        )
        interactor.fetchAlbum(albumId)

        verify(repository).getAlbum(albumId)
        verify(presenter).presentAlbum(album)
    }
}