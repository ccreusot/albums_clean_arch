package com.ccreusot.albums.repositories

import android.support.annotation.VisibleForTesting
import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitAlbumsRepository : AlbumsRepository {

    private val service: PhotosApi by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(PhotosApi::class.java)
    }

    override fun getAlbums(): List<Album> {
        try {
            val response = service.getPhotos().execute()
            if (response.isSuccessful) {
                val photos = response.body()
                return transformIntoAlbums(photos)
            }
            throw Exception("code : ${response.code()}  msg: ${response.message()}")
        } catch (e: Exception) {
            throw e
        }
    }

    @VisibleForTesting
    internal fun transformIntoAlbums(photos : List<PhotoAlbum>?) : List<Album> {
        val albums : MutableList<Album> = ArrayList()
        photos?.forEach {
            val albumId = it.albumId
            var album = albums.firstOrNull { it.id == albumId }
            if (album == null) {
                album = Album(albumId, ArrayList())
                albums.add(album)
            }
            album.photos.add(Photo(it.id, it.title, it.url, it.thumbnailsUrl))
        }
        return albums
    }

    data class PhotoAlbum(@SerializedName("albumId") val albumId: Long,
                          @SerializedName("id") val id: Long,
                          @SerializedName("title") val title: String,
                          @SerializedName("url") val url: String,
                          @SerializedName("thumbnailUrl") val thumbnailsUrl: String)

    interface PhotosApi {
        @GET("photos")
        fun getPhotos(): Call<List<PhotoAlbum>>
    }
}