package com.saltpay.music.top.ui.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.saltpay.music.top.R
import com.saltpay.music.top.ui.screens.common.PlaceholderScreen
import com.saltpay.music.top.ui.screens.common.ProgressBarScreen
import com.saltpay.music.top.ui.screens.common.WarningScreen
import com.saltpay.music.top.ui.screens.common.model.RegularUiState
import com.saltpay.music.top.ui.screens.detail.content.DetailAlbumContent

@Composable
fun DetailAlbumScreen(
    albumId: String,
    viewModel: DetailAlbumScreenViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit){
        viewModel.getAlbumById(albumId)
    }

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is RegularUiState.Loading -> ProgressBarScreen()
        is RegularUiState.NoData -> PlaceholderScreen(text = R.string.not_data)
        is RegularUiState.Data -> DetailAlbumContent(
            (uiState as RegularUiState.Data).value,
            viewModel::handleFavorite,
            onBack
        )
        is RegularUiState.Error -> {
            WarningScreen(
                (uiState as RegularUiState.Error).e.localizedMessage
                    ?: stringResource(id = R.string.error_occurred)
            )
        }
    }
}


