package com.ccreusot.albums.presentations

import com.ccreusot.albums.viewmodels.PhotoViewModel

interface AlbumDetailView {
    fun displayAlbumIdNegative()
    fun displayAlbumError()
    fun displayPhotos(photoViewModelList: List<PhotoViewModel>)
}