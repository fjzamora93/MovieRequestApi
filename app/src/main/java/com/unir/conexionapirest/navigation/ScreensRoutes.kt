package com.unir.conexionapirest.navigation

sealed class ScreensRoutes(val route: String) {
    object MainScreen : ScreensRoutes("MainScreen")
    object FavScreen : ScreensRoutes("FavScreen")
    object MovieDetailScreen : ScreensRoutes("MovieDetailScreen/{movieID}") {
        fun createRoute(movieID: String) = "MovieDetailScreen/$movieID"
    }

}