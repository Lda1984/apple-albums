package com.saltpay.music.top.domian.usecase.album

import com.saltpay.music.top.domian.AlbumsRepository
import javax.inject.Inject

class FetchAlbumUseCase @Inject constructor(
    private val repository: AlbumsRepository
) : suspend () -> Unit {

    override suspend fun invoke() {
        repository.fetch()
    }
}