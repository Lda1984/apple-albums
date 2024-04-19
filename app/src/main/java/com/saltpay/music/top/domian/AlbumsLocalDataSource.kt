package com.saltpay.music.top.domian

import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow

/**
 * A local source for albums
 */
interface AlbumsLocalDataSource {
    suspend fun setCache(list: Flow<List<Album>>)
    fun getCache(): Flow<List<Album>>
    suspend fun handleFavorite(album: Album)
}