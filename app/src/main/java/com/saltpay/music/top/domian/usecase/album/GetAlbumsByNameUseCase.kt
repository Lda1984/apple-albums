package com.saltpay.music.top.domian.usecase.album

import com.saltpay.music.top.domian.AlbumsRepository
import com.saltpay.music.top.domian.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsByNameUseCase @Inject constructor(
    private val repository: AlbumsRepository
) : (String) -> Flow<List<Album>> {

    override fun invoke(name: String): Flow<List<Album>> {
        return repository.getAlbumsByName(name)
    }

}