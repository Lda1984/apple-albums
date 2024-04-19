package com.saltpay.music.top.data.albums

import com.saltpay.music.top.domian.AlbumsLocalDataSource
import com.saltpay.music.top.domian.FavoriteRepository
import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import logcat.logcat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsLocalDataSourceImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : AlbumsLocalDataSource {
    private val _list = MutableStateFlow<List<Album>>(emptyList())

    override suspend fun setCache(list: Flow<List<Album>>) {
        list.zip(favoriteRepository.getFavorites) { inputAlbums, favorites ->
            val newAlbums = inputAlbums.toMutableList()
            newAlbums.sortBy { it.name }

            val favoriteAlbums = newAlbums.filter { favorites.contains(it.id) }

            favoriteAlbums.forEach {
                val index = newAlbums.indexOf(it)
                if (index > -1) {
                    newAlbums[index] = it.copy(isFavorite = true)
                }
            }
            newAlbums
        }.collectLatest {
            logcat(tag = "AlbumsLocalDataSourceImpl") { "========it:${it.size}" }
            _list.value = it
        }
    }

    override fun getCache(): Flow<List<Album>> {
        return _list.asStateFlow()
    }

    override suspend fun handleFavorite(album: Album) {
        val index = _list.value.indexOf(album)
        if (index > -1) {
            val newList = _list.value.toMutableList()

            val newValue = album.isFavorite.not()
            val newAlbumEntity = album.copy(isFavorite = newValue)
            newList[index] = newAlbumEntity
            if (newValue) {
                favoriteRepository.addFavorite(newAlbumEntity.id)
            } else {
                favoriteRepository.removeFavorite(newAlbumEntity.id)
            }

            _list.value = newList
        }
    }
}