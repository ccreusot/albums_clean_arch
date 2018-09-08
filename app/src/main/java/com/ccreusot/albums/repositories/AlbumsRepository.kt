package com.ccreusot.albums.repositories

import com.ccreusot.albums.entities.Album

interface AlbumsRepository {
    @Throws(Exception::class)
    fun getAlbums() : List<Album>
}