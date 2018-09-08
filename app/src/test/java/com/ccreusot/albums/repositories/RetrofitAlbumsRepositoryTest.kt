package com.ccreusot.albums.repositories

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RetrofitAlbumsRepositoryTest {

    @Test
    fun test_transformIntoAlbums_withNullPhotos() {
        val repository = RetrofitAlbumsRepository()

        val albums = repository.transformIntoAlbums(null)

        assertThat(albums).isEmpty()
    }

    @Test
    fun test_transformIntoAlbums_withEmptyList() {
        val repository = RetrofitAlbumsRepository()

        val albums = repository.transformIntoAlbums(emptyList())

        assertThat(albums).isEmpty()
    }

    @Test
    fun test_transformIntoAlbums() {
        val photos = listOf<RetrofitAlbumsRepository.PhotoAlbum>(
                RetrofitAlbumsRepository.PhotoAlbum(0, 0, "title", "url", "thumbnailUrl"),
                RetrofitAlbumsRepository.PhotoAlbum(0, 1, "title", "url", "thumbnailUrl"),
                RetrofitAlbumsRepository.PhotoAlbum(0, 2, "title", "url", "thumbnailUrl"),
                RetrofitAlbumsRepository.PhotoAlbum(1, 0, "title", "url", "thumbnailUrl"),
                RetrofitAlbumsRepository.PhotoAlbum(1, 2, "title", "url", "thumbnailUrl")
        )
        val repository = RetrofitAlbumsRepository()

        val albums = repository.transformIntoAlbums(photos)

        assertThat(albums.size).isEqualTo(2)
        assertThat(albums[0].id).isEqualTo(0)
        assertThat(albums[1].id).isEqualTo(1)
        assertThat(albums[0].photos.size).isEqualTo(3)
        assertThat(albums[1].photos.size).isEqualTo(2)
    }
}