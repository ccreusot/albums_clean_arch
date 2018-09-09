package com.ccreusot.albums.repositories

import android.content.Context
import android.content.SharedPreferences
import com.ccreusot.albums.entities.Album
import com.ccreusot.albums.entities.Photo
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test
import org.junit.internal.runners.statements.ExpectException
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CacheAlbumsRepositoryTest {

    companion object {
        private const val ONE_DAY = 24 * 60 * 60 * 1000
    }

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var editor : SharedPreferences.Editor

    @Mock
    private lateinit var albumsRepository: AlbumsRepository


    @Test(expected = Exception::class)
    fun test_getAlbums_noCacheSaved_exceptionRaised() {
        val cacheAlbumsRepository = CacheAlbumsRepository(context, albumsRepository)

        `when`(context.getSharedPreferences(Matchers.anyString(), Matchers.anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString(Matchers.anyString(), Matchers.anyString())).thenReturn("")
        `when`(sharedPreferences.getLong(Matchers.anyString(), Matchers.anyLong())).thenReturn(0)

        doThrow(Exception::class.java).`when`(albumsRepository).getAlbums()

        cacheAlbumsRepository.getAlbums()
    }

    @Test
    fun test_getAlbums_noCacheSaved() {
        val cacheAlbumsRepository = CacheAlbumsRepository(context, albumsRepository)

        `when`(context.getSharedPreferences(Matchers.anyString(), Matchers.anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString(Matchers.anyString(), Matchers.anyString())).thenReturn("")
        `when`(sharedPreferences.getLong(Matchers.anyString(), Matchers.anyLong())).thenReturn(0)
        `when`(sharedPreferences.edit()).thenReturn(editor)

        val albumList = listOf(
                Album(0, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                )),
                Album(1, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                ))
        )
        `when`(albumsRepository.getAlbums()).thenReturn(
                albumList)
        val albums = cacheAlbumsRepository.getAlbums()

        assertThat(albums).isEqualTo(albumList)
        verify(sharedPreferences).edit()
        verify(editor).putString(Matchers.anyString(), Matchers.anyString())
        verify(editor).putLong(Matchers.anyString(), Matchers.anyLong())
    }

    @Test
    fun test_getAlbums_cacheSaved_sinceYesterday() {
        val cacheAlbumsRepository = CacheAlbumsRepository(context, albumsRepository)
        val albumList = listOf(
                Album(0, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                )),
                Album(1, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                ))
        )

        `when`(context.getSharedPreferences(Matchers.anyString(), Matchers.anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString(Matchers.anyString(), Matchers.anyString())).thenReturn(Gson().toJson(albumList))
        `when`(sharedPreferences.getLong(Matchers.anyString(), Matchers.anyLong())).thenReturn(Calendar.getInstance().timeInMillis - ONE_DAY * 2)
        `when`(sharedPreferences.edit()).thenReturn(editor)

        `when`(albumsRepository.getAlbums()).thenReturn(
                albumList)
        val albums = cacheAlbumsRepository.getAlbums()

        assertThat(albums).isEqualTo(albumList)
        verify(sharedPreferences).edit()
        verify(editor).putString(Matchers.anyString(), Matchers.anyString())
        verify(editor).putLong(Matchers.anyString(), Matchers.anyLong())
    }

    @Test
    fun test_getAlbums() {
        val cacheAlbumsRepository = CacheAlbumsRepository(context, albumsRepository)
        val albumList = listOf(
                Album(0, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                )),
                Album(1, mutableListOf(
                        Photo(0, "test", "url", "thumbnail"),
                        Photo(1, "test", "url", "thumbnail"),
                        Photo(2, "test", "url", "thumbnail")
                ))
        )

        `when`(context.getSharedPreferences(Matchers.anyString(), Matchers.anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.getString(Matchers.anyString(), Matchers.anyString())).thenReturn(Gson().toJson(albumList))
        `when`(sharedPreferences.getLong(Matchers.anyString(), Matchers.anyLong())).thenReturn(Calendar.getInstance().timeInMillis)
        `when`(sharedPreferences.edit()).thenReturn(editor)

        val albums = cacheAlbumsRepository.getAlbums()

        assertThat(albums).isEqualTo(albumList)
        verify(sharedPreferences).getString(Matchers.anyString(), Matchers.anyString())
        verify(sharedPreferences).getLong(Matchers.anyString(), Matchers.anyLong())
        verifyNoMoreInteractions(sharedPreferences)
        verifyZeroInteractions(albumsRepository)
    }
}