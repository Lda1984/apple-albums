package com.saltpay.music.top.data.albums

import com.saltpay.music.top.data.albums.mapper.AlbumMapper
import com.saltpay.music.top.domian.AlbumsRemoteDataSource
import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRemoteDataSourceImpl @Inject constructor(
    private val apiAlbums: ApiAlbums,
    private val albumMapper: AlbumMapper
) : AlbumsRemoteDataSource {

    override fun getAlbums(): Flow<List<Album>> {
        return flow {
            val list = apiAlbums.getTopAlbums().dematerialize().feedDto?.entry
            val newList = list?.map(albumMapper::fromDtoToDomain) ?: emptyList()
            emit(newList)
        }
    }

}