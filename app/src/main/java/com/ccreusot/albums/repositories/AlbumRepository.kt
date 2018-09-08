package com.ccreusot.albums.repositories

import com.ccreusot.albums.entities.Album

interface AlbumRepository {
    @Throws(Exception::class)
    fun getAlbum(albumId: Long) : Album

}
