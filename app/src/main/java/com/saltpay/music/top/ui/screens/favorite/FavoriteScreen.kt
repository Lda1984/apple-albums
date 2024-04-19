package com.saltpay.music.top.ui.screens.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saltpay.music.top.R
import com.saltpay.music.top.ui.screens.common.PlaceholderScreen
import com.saltpay.music.top.ui.screens.common.ProgressBarScreen
import com.saltpay.music.top.ui.screens.common.WarningScreen
import com.saltpay.music.top.ui.screens.common.model.RegularUiState
import com.saltpay.music.top.ui.screens.favorite.content.FavoriteContent

@Composable
fun FavoriteScreen(
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                text = stringResource(id = R.string.tab_favorite_label),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        when (uiState) {
            is RegularUiState.Loading -> ProgressBarScreen()
            is RegularUiState.NoData -> PlaceholderScreen(text = R.string.not_data)
            is RegularUiState.Data -> FavoriteContent(
                (uiState as RegularUiState.Data).value,
                onNavigateToDetail,
                viewModel::handleFavorite
            )

            is RegularUiState.Error -> {
                WarningScreen(
                    (uiState as RegularUiState.Error).e.localizedMessage
                        ?: stringResource(id = R.string.error_occurred)
                )
            }
        }
    }
}
