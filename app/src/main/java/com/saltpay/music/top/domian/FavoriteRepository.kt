package com.saltpay.music.top.domian

import kotlinx.coroutines.flow.Flow

/**
 * A storage for favorite albums
 */
interface FavoriteRepository {
    val getFavorites: Flow<Set<String>>
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
}