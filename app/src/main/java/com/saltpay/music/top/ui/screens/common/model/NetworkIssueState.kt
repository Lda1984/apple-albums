package com.saltpay.music.top.ui.screens.common.model

sealed interface NetworkIssueState {
    object NoIssue : NetworkIssueState
    data class Issue(val e: Throwable): NetworkIssueState
}