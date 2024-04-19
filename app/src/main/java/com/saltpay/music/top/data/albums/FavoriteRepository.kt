package com.saltpay.music.top.data.albums

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.saltpay.music.top.domian.FavoriteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context):
    FavoriteRepository {

    private companion object {
        const val DATASTORE = "favorite_datastore"
        val FAVORITES = stringSetPreferencesKey("favorites")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)

    override val getFavorites: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES]  ?: emptySet()
        }

    override suspend fun addFavorite(id: String) {
        context.dataStore.edit { preferences ->
            val list = preferences[FAVORITES]?.toMutableSet() ?: mutableSetOf()
            list.add(id)
            preferences[FAVORITES] = list
        }
    }

    override suspend fun removeFavorite(id: String) {
        context.dataStore.edit { preferences ->
            val list = preferences[FAVORITES]?.toMutableSet() ?: mutableSetOf()
            list.remove(id)
            preferences[FAVORITES] = list
        }
    }

}