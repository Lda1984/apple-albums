package com.saltpay.music.top.ui.screens.common.model

sealed interface RegularUiState<out T: Any> {
    object Loading : RegularUiState<Nothing>
    object NoData : RegularUiState<Nothing>
    data class Data<out T: Any>(val value: T): RegularUiState<T>
    data class Error(val e: Throwable): RegularUiState<Nothing>
}