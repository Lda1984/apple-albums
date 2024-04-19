package com.saltpay.music.top.ui.screens.toplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.usecase.album.FetchAlbumUseCase
import com.saltpay.music.top.domian.usecase.album.GetAlbumsByNameUseCase
import com.saltpay.music.top.domian.usecase.album.HandleFavoriteUseCase
import com.saltpay.music.top.ui.screens.common.model.NetworkIssueState
import com.saltpay.music.top.ui.screens.common.model.RegularUiState
import com.saltpay.music.top.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import logcat.logcat
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TopListScreenViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val fetchAlbumUseCase: FetchAlbumUseCase,
    private val getAlbumsByNameUseCase: GetAlbumsByNameUseCase,
    private val handleFavoriteUseCase: HandleFavoriteUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<RegularUiState<List<Album>>>(RegularUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val searchFlow = MutableStateFlow("")
    val isAutoUpdate = MutableStateFlow(false)

    private val _networkIssue = MutableStateFlow<NetworkIssueState>(NetworkIssueState.NoIssue)
    val networkIssue = _networkIssue.asStateFlow()

    private val _isUpdateProcess = MutableStateFlow(false)
    val isUpdateProcess = _isUpdateProcess.asStateFlow()

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            fetch()

            searchFlow.flatMapLatest { search ->
                getAlbumsByNameUseCase(search)
            }.catch {
                _uiState.value = RegularUiState.Error(it)
            }.onEach {
                if (it.isEmpty()) {
                    _uiState.value = RegularUiState.NoData
                } else {
                    _uiState.value = RegularUiState.Data(it)
                }
            }.launchIn(this)

            isAutoUpdate
                .collectLatest {
                    if (it) {
                        while (true) {
                            fetch()
                            delay(5000)
                        }
                    } else {
                        logcat { "false" }
                    }
                }
        }
    }

    fun handleEvent(event: TopListEvent) {
        when (event) {
            is TopListEvent.ChangeAutoUpdate -> onChangeAutoUpdate(event.enabled)
            is TopListEvent.ChangeFilter -> onChangeFilter(event.text)
            is TopListEvent.HandleFavorite -> onHandleFavorite(event.album)
        }
    }

    private fun onChangeFilter(it: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            searchFlow.value = it
        }
    }

    private fun onChangeAutoUpdate(enabled: Boolean) {
        viewModelScope.launch(dispatcherProvider.main) {
            isAutoUpdate.value = enabled
        }
    }

    private fun onHandleFavorite(album: Album) {
        viewModelScope.launch(dispatcherProvider.main) {
            handleFavoriteUseCase(album)
        }
    }

    private suspend fun fetch() {
        try {
            _isUpdateProcess.value = true
            delay(2000) // Simulation network's delay
            fetchAlbumUseCase()

            _networkIssue.emit(NetworkIssueState.NoIssue)
        } catch (e: Exception) {
            _networkIssue.emit(NetworkIssueState.Issue(e))
            logcat { "error:$e" }
        } finally {
            _isUpdateProcess.value = false
        }
    }
}
