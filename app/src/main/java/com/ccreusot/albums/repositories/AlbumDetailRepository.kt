package com.ccreusot.albums.repositories

import com.ccreusot.albums.entities.Album

interface AlbumDetailRepository {
    @Throws(Exception::class)
    fun getAlbum(albumId: Long) : Album

}
