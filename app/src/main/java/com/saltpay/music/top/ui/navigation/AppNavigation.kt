package com.saltpay.music.top.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.saltpay.music.top.R

sealed class AppNavigation(val route: String) {

    sealed interface Screen {
        object TopList : AppNavigation("TopList")
        object Favorite : AppNavigation("Favorite")
        object Album : AppNavigation("Album")
    }

    sealed class BottomBarTab(
        route: String,
        @DrawableRes val icon: Int,
        @StringRes val label: Int,
    ) : AppNavigation(route) {

        object TopListTab : BottomBarTab(
            route = Screen.TopList.route,
            icon = R.drawable.ic_top,
            label = R.string.tab_top_list_label
        )

        object FavoriteTab : BottomBarTab(
            route = Screen.Favorite.route,
            icon = R.drawable.ic_favorite_red,
            label = R.string.tab_favorite_label
        )
    }
}