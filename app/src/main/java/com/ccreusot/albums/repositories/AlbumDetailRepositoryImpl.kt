package com.ccreusot.albums.repositories

import android.content.Context
import com.ccreusot.albums.entities.Album

class AlbumDetailRepositoryImpl(private val repository : AlbumsRepository) : AlbumDetailRepository {

    override fun getAlbum(albumId: Long): Album = repository.getAlbums().firstOrNull { it.id == albumId } ?: throw Exception("album doesn't exist.")
}