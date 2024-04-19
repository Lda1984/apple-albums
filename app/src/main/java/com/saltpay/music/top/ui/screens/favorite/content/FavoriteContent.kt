package com.saltpay.music.top.ui.screens.favorite.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.ui.screens.common.AlbumCard

@Composable
fun FavoriteContent(
    data: List<Album>,
    navigateToDetail: (String) -> Unit,
    handleFavorite: (Album) -> Unit
) {
    LazyColumn {
        this.items(data) { item ->
            AlbumCard(
                item,
                handleFavorite,
                navigateToDetail,
            )
        }
    }
}