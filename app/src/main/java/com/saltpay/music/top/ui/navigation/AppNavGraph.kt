package com.saltpay.music.top.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saltpay.music.top.ui.screens.detail.DetailAlbumScreen
import com.saltpay.music.top.ui.screens.favorite.FavoriteScreen
import com.saltpay.music.top.ui.screens.toplist.TopListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation.Screen.TopList.route,
        modifier = modifier
    ) {

        val onNavigateToDetail: (String) -> Unit = { id ->
            navController.navigate("${AppNavigation.Screen.Album.route}/${id}/detail")
        }

        composable(AppNavigation.Screen.TopList.route) {
            TopListScreen { id ->
                onNavigateToDetail(id)
            }
        }
        composable(AppNavigation.Screen.Favorite.route) {
            FavoriteScreen { id ->
                onNavigateToDetail(id)
            }
        }
        composable("${AppNavigation.Screen.Album.route}/{albumId}/detail") {
            val albumId = it.arguments?.getString("albumId") ?: ""
            DetailAlbumScreen(albumId) {
                navController.popBackStack()
            }
        }
    }

}