package com.saltpay.music.top.data.albums

import com.saltpay.music.top.domian.AlbumsLocalDataSource
import com.saltpay.music.top.domian.AlbumsRemoteDataSource
import com.saltpay.music.top.domian.AlbumsRepository
import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
    private val remoteDataSource: AlbumsRemoteDataSource,
    private val localDataSource: AlbumsLocalDataSource
) : AlbumsRepository {

    override suspend fun fetch() {
        localDataSource.setCache(remoteDataSource.getAlbums())
    }

    override fun getAlbumsByName(value: String): Flow<List<Album>> {
        return localDataSource.getCache().map { list ->
            if (value.isBlank()) {
                list
            } else {
                list.filter { it.name.contains(value, true) }
            }
        }
    }

    override suspend fun handleFavorite(album: Album) {
        localDataSource.handleFavorite(album)
    }

    override fun getAlbumsById(id: String): Flow<List<Album>> {
        return localDataSource.getCache().map {
            it.filter { album ->
                album.id == id
            }
        }
    }

    override fun getFavoriteAlbums(): Flow<List<Album>> {
        return localDataSource.getCache().map {
            it.filter { album ->
                album.isFavorite
            }
        }
    }
}






