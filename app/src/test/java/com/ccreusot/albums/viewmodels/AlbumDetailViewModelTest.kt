package com.ccreusot.albums.viewmodels

import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AlbumDetailViewModelTest {

    @Test
    fun test_getPhotoCount() {
        val album = Album(0, mutableListOf(
                Photo(0, "title", "url", "thumbnailUrl"),
                Photo(0, "title", "url", "thumbnailUrl2"),
                Photo(0, "title", "url", "thumbnailUrl3")
        ))
        val viewModel = AlbumViewModel(album)

        val photoCount = viewModel.getPhotoCount()

        assertThat(photoCount).isEqualTo(3)
    }

    @Test
    fun test_getThumbnails() {
        val thumbnailUrl = "thumbnailUrl"
        val thumbnailUrl2 = "thumbnailUrl2"
        val thumbnailUrl3 = "thumbnailUrl3"
        val album = Album(0, mutableListOf(
                Photo(0, "title", "url", thumbnailUrl),
                Photo(0, "title", "url", thumbnailUrl2),
                Photo(0, "title", "url", thumbnailUrl3),
                Photo(0, "title", "url", "thumbnailUrl4")
        ))
        val viewModel = AlbumViewModel(album)

        val thumbnails = viewModel.getThumbnails()


        assertThat(thumbnails.size).isEqualTo(3)
        assertThat(thumbnails).contains(thumbnailUrl)
        assertThat(thumbnails).contains(thumbnailUrl2)
        assertThat(thumbnails).contains(thumbnailUrl3)
    }
}