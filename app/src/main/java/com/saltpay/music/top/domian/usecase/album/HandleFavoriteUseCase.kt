package com.saltpay.music.top.domian.usecase.album

import com.saltpay.music.top.domian.AlbumsRepository
import com.saltpay.music.top.domian.model.Album
import javax.inject.Inject

class HandleFavoriteUseCase @Inject constructor(
    private val repository: AlbumsRepository
) : suspend (Album) -> Unit {

    override suspend fun invoke(album: Album) {
        repository.handleFavorite(album)
    }
}