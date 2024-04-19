package com.saltpay.music.top.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saltpay.music.top.domian.AlbumsRepository
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.ui.screens.common.model.RegularUiState
import com.saltpay.music.top.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAlbumScreenViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegularUiState<Album>>(RegularUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAlbumById(albumId: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            albumsRepository.getAlbumsById(albumId)
                .catch {
                    _uiState.value = RegularUiState.Error(it)
                }
                .collect {
                if (it.isEmpty()) {
                    _uiState.value = RegularUiState.NoData
                } else {
                    _uiState.value = RegularUiState.Data(it.first())
                }
            }
        }
    }

    fun handleFavorite(album: Album) {
        viewModelScope.launch(dispatcherProvider.main) {
            albumsRepository.handleFavorite(album)
        }
    }
}