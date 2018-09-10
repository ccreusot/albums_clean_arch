package com.ccreusot.albums.repositories

import com.ccreusot.albums.entities.Album
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumDetailRepositoryImplTest {

    @Test(expected = Exception::class)
    fun test_getAlbums_exceptionRaised() {
        val albumId = -1L
        val albumRepository = mock(AlbumsRepository::class.java)
        val repositoryImpl = AlbumDetailRepositoryImpl(albumRepository)

        doThrow(Exception::class.java).`when`(albumRepository.getAlbums())

        repositoryImpl.getAlbum(albumId)
    }

    @Test(expected = Exception::class)
    fun test_getAlbums_albumsListIsEmpty() {
        val albumId = 10L
        val albumRepository = mock(AlbumsRepository::class.java)
        val repositoryImpl = AlbumDetailRepositoryImpl(albumRepository)

        `when`(albumRepository.getAlbums()).thenReturn(
                listOf(
                )
        )

        repositoryImpl.getAlbum(albumId)
    }

    @Test(expected = Exception::class)
    fun test_getAlbums_albumDoesNotExist() {
        val albumId = -1L
        val albumRepository = mock(AlbumsRepository::class.java)
        val repositoryImpl = AlbumDetailRepositoryImpl(albumRepository)

        `when`(albumRepository.getAlbums()).thenReturn(
                listOf(
                        Album(0, mutableListOf()),
                        Album(1, mutableListOf()),
                        Album(2, mutableListOf())
                )
        )

        repositoryImpl.getAlbum(albumId)
    }

    fun test_getAlbums() {
        val albumId = 2L
        val albumRepository = mock(AlbumsRepository::class.java)
        val repositoryImpl = AlbumDetailRepositoryImpl(albumRepository)

        val albums = listOf(
                Album(0, mutableListOf()),
                Album(1, mutableListOf()),
                Album(2, mutableListOf())
        )
        `when`(albumRepository.getAlbums()).thenReturn(
                albums
        )

        val album = repositoryImpl.getAlbum(albumId)

        assertThat(album).isEqualTo(albums[2])
    }

}