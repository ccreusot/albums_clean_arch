package com.ccreusot.albums.repositories

import android.content.Context
import com.ccreusot.albums.entities.Album

class AlbumDetailRepositoryImpl(context: Context) : AlbumDetailRepository {

    private val cashAlbumRepository = CacheAlbumsRepository(context, RetrofitAlbumsRepository())

    override fun getAlbum(albumId: Long): Album = cashAlbumRepository.getAlbums().firstOrNull { it.id == albumId } ?: throw Exception("album doesn't exist.")
}