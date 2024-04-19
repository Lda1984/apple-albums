package com.saltpay.music.top.domian

import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow

/**
 * Interface represents a repository for albums
 */
interface AlbumsRepository {
    suspend fun fetch()
    fun getAlbumsByName(value: String): Flow<List<Album>>
    suspend fun handleFavorite(album: Album)
    fun getAlbumsById(id: String): Flow<List<Album>>
    fun getFavoriteAlbums(): Flow<List<Album>>
}