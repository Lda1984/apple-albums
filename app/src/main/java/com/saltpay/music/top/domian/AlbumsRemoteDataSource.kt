package com.saltpay.music.top.domian

import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow

/**
 * A remote source for albums
 */
interface AlbumsRemoteDataSource {
    fun getAlbums(): Flow<List<Album>>
}