package com.saltpay.music.top.ui.screens.toplist

import com.saltpay.music.top.domian.model.Album

sealed interface TopListEvent {

    data class ChangeAutoUpdate(val enabled: Boolean) : TopListEvent
    data class ChangeFilter(val text: String) : TopListEvent
    data class HandleFavorite(val album: Album) : TopListEvent
}
