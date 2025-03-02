package com.unir.conexionapirest.navigation

sealed class ScreensRoutes(val route: String) {
    object MainScreen : ScreensRoutes("MainScreen")
    object DetailScreen : ScreensRoutes("DetailScreen/{movieID}") {
        fun createRoute(id: String) = "DetailScreen/$id"
    }

}