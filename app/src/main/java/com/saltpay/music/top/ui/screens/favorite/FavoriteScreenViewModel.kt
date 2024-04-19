package com.saltpay.music.top.ui.screens.favorite

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val dispatcherProvider: DispatcherProvider
): ViewModel()  {


    private val _uiState =
        MutableStateFlow<RegularUiState<List<Album>>>(RegularUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getFavorite()
    }

    private fun getFavorite() {
        viewModelScope.launch(dispatcherProvider.main) {
            albumsRepository.getFavoriteAlbums()
                .catch {
                    _uiState.value = RegularUiState.Error(it)
                }
                .collectLatest {
                    if (it.isEmpty()) {
                        _uiState.value = RegularUiState.NoData
                    } else {
                        _uiState.value = RegularUiState.Data(it)
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