package com.saltpay.music.top.ui.screens.toplist.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saltpay.music.top.R
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.model.Artist
import com.saltpay.music.top.domian.model.Category
import com.saltpay.music.top.domian.model.Price
import com.saltpay.music.top.ui.screens.common.AlbumCard
import com.saltpay.music.top.ui.screens.common.PlaceholderScreen
import com.saltpay.music.top.ui.screens.common.ProgressBarScreen
import com.saltpay.music.top.ui.screens.common.WarningScreen
import com.saltpay.music.top.ui.screens.common.model.NetworkIssueState
import com.saltpay.music.top.ui.screens.common.model.RegularUiState
import com.saltpay.music.top.ui.screens.toplist.TopListEvent
import com.saltpay.music.top.ui.screens.toplist.TopListEvent.ChangeAutoUpdate
import com.saltpay.music.top.ui.screens.toplist.TopListEvent.ChangeFilter
import com.saltpay.music.top.ui.screens.toplist.TopListEvent.HandleFavorite
import com.saltpay.music.top.ui.theme.MainApplicationTheme

@Composable
fun TopListContent(
    uiState: RegularUiState<List<Album>>,
    searchText: String,
    isAutoUpdate: Boolean,
    isUpdateProcess: Boolean,
    networkIssue: NetworkIssueState,
    handleEvent: (TopListEvent) -> Unit,
    onNavigateToDetail: (String) -> Unit,
) {
    Column {
        HandleNetworkIssue(networkIssue)
        Row(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                text = stringResource(id = R.string.tab_top_list_label),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (isUpdateProcess) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    text = stringResource(id = R.string.auto_update_title)
                )
                Switch(
                    checked = isAutoUpdate,
                    onCheckedChange = { handleEvent(ChangeAutoUpdate(it)) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        TextField(
            value = searchText,
            onValueChange = { handleEvent(ChangeFilter(it)) },
            Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchText.isEmpty()) return@TextField
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { handleEvent(ChangeFilter("")) }
                )
            },
            label = { Text(stringResource(id = R.string.search_by_name)) }
        )
        when (uiState) {
            is RegularUiState.Loading -> ProgressBarScreen()
            is RegularUiState.NoData -> PlaceholderScreen(text = R.string.not_data)
            is RegularUiState.Data -> AlbumList(
                data = uiState.value,
                onNavigateToDetail = onNavigateToDetail,
                onHandleFavorite = {
                    handleEvent(HandleFavorite(it))
                }
            )

            is RegularUiState.Error -> {
                WarningScreen(
                    text = uiState.e.localizedMessage
                        ?: stringResource(id = R.string.error_occurred)
                )
            }
        }
    }
}


@Composable
private fun AlbumList(
    data: List<Album>,
    onNavigateToDetail: (String) -> Unit,
    onHandleFavorite: (Album) -> Unit
) {
    LazyColumn {
        items(data) { item ->
            AlbumCard(
                item,
                onHandleFavorite,
                onNavigateToDetail)
        }
    }
}

@Composable
fun HandleNetworkIssue(networkIssueState: NetworkIssueState) {
    when (networkIssueState) {
        is NetworkIssueState.Issue -> {
            Snackbar(
                modifier = Modifier.padding(8.dp)
            ) { networkIssueState.e.localizedMessage?.let { Text(text = it) } }
        }

        else -> {
            // NO-OP
        }
    }
}


@Composable
@Preview
private fun TopListContentPreview() {
    val list = List(5) {
        Album(
            it.toString(), Artist("$it album name", "href"), "$it name", "imageUrl", 10,
            Price(10f, "usd"), Category(1, "term", "label", "schemeUrl"), null
        )
    }

    MainApplicationTheme {
        TopListContent(
            uiState = RegularUiState.Data(list),
            searchText = "searchText",
            isAutoUpdate = true,
            isUpdateProcess = true,
            networkIssue = NetworkIssueState.NoIssue,
            handleEvent = {},
            onNavigateToDetail = {})
    }
}