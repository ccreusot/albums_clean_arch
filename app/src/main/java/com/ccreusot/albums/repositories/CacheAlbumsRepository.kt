package com.ccreusot.albums.repositories

import android.content.Context
import com.ccreusot.albums.entities.Album
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class CacheAlbumsRepository(private val context: Context, private val albumsRepository: AlbumsRepository) : AlbumsRepository {

    companion object {
        private const val ALBUM_SHARED_PREF_CACHE = "album_shared_pref_cache"
        private const val ALBUM_LIST_CACHE = "album_list_cache"
        private const val ALBUM_LIST_LAST_SAVE = "album_list_last_save"
        private const val CACHE_EXPIRATION = 24 * 60 * 60 * 1000 // For 24h of cache
     }

    private val gson = Gson()

    override fun getAlbums(): List<Album> {
        val sharedPreferences = context.getSharedPreferences(ALBUM_SHARED_PREF_CACHE, Context.MODE_PRIVATE)
        val jsonCacheValue = sharedPreferences.getString(ALBUM_LIST_CACHE, "")
        val lastSaveInMillis = sharedPreferences.getLong(ALBUM_LIST_LAST_SAVE, 0)
        if (jsonCacheValue.isNullOrEmpty() || (Calendar.getInstance().timeInMillis - lastSaveInMillis) > CACHE_EXPIRATION) {
            try {
                val albums = albumsRepository.getAlbums()
                with(sharedPreferences.edit()) {
                    putString(ALBUM_LIST_CACHE, gson.toJson(albums))
                    putLong(ALBUM_LIST_LAST_SAVE, Calendar.getInstance().timeInMillis)
                    commit()
                }
                return albums
            } catch (e: Exception) {
                throw e
            }
        } else {
            val listAlbumType = object : TypeToken<List<Album>>() {}.type
            val album: List<Album> = gson.fromJson(jsonCacheValue, listAlbumType)
            return album
        }
    }
}