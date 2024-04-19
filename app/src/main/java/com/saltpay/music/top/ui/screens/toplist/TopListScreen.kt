package com.saltpay.music.top.ui.screens.toplist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.saltpay.music.top.ui.screens.toplist.content.TopListContent

@Composable
fun TopListScreen(
    viewModel: TopListScreenViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    val searchText by viewModel.searchFlow.collectAsState()
    val isAutoUpdate by viewModel.isAutoUpdate.collectAsState()
    val isUpdateProcess by viewModel.isUpdateProcess.collectAsState()
    val networkIssue by viewModel.networkIssue.collectAsState()

    TopListContent(
        uiState = uiState,
        searchText = searchText,
        isAutoUpdate = isAutoUpdate,
        isUpdateProcess = isUpdateProcess,
        networkIssue = networkIssue,
        handleEvent = viewModel::handleEvent,
        onNavigateToDetail = onNavigateToDetail
    )
}

