package com.ccreusot.albums.interactors

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.ccreusot.albums.presentations.AlbumsPresenter
import com.ccreusot.albums.repositories.AlbumsRepository
import org.junit.Test
import org.mockito.Mockito.*

class AlbumsInteractorTest {
    val presenter = mock(AlbumsPresenter::class.java)
    val repository = mock(AlbumsRepository::class.java)
    val interactor = AlbumsInteractor(presenter, repository)

    @Test
    fun test_fetchAlbums_repositoryExceptionRaised() {
        doThrow(Exception()).`when`(repository).getAlbums()

        interactor.fetchAlbums()

        verify(repository).getAlbums()
        verify(presenter).presentAlbumsError()
    }

    @Test
    fun test_fetchAlbums_emptyAlbumList() {
        `when`(repository.getAlbums()).thenReturn(emptyList())

        interactor.fetchAlbums()

        verify(repository).getAlbums()
        verify(presenter).presentEmptyAlbumList()
    }

    @Test
    fun test_fetchAlbums() {
        val albums = listOf(
                Album(0, mutableListOf(Photo(0, "photo0", "url", "thumbnailUrl"))),
                Album(1, mutableListOf(Photo(0, "photo0", "url", "thumbnailUrl")))
        )
        `when`(repository.getAlbums()).thenReturn(albums)

        interactor.fetchAlbums()

        verify(repository).getAlbums()
        verify(presenter).presentAlbums(albums)
    }
}