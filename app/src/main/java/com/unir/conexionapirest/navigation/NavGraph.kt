package com.unir.conexionapirest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unir.conexionapirest.ui.screens.MainScreen
import com.unir.conexionapirest.ui.screens.MovieDetailScreen


@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer el NavigationViewModel en todo el árbol de composables dentro de NavGraph
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    CompositionLocalProvider(LocalNavigationViewModel provides navigationViewModel) {
        // Declaramos el objeto que va a ser observado
        val navigationEvent by navigationViewModel.navigationEvent.observeAsState()

        // Este LaunchedEffect es un OBSERVADOR que se disparará ante cualquier evento de navegación
        LaunchedEffect(navigationEvent) {
            navigationEvent?.let { event ->
                when (event) {
                    is NavigationEvent.Navigate -> {
                        navController.navigate(event.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    is NavigationEvent.NavigateAndPopUp -> {
                        navController.navigate(event.route) {
                            popUpTo(event.popUpToRoute) {
                                inclusive = event.inclusive
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
                navigationViewModel.clearNavigationEvent()
            }
        }

        // DEFINICIÓN DE LAS RUTAS DE CADA PANTALLA
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.MainScreen.route
        ) {

            // Pantalla principal
            composable(ScreensRoutes.MainScreen.route) {
                MainScreen()
            }


            // Pantalla de detalles
            composable(
                ScreensRoutes.DetailScreen.route,
                arguments = listOf(navArgument("movieID") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieID") ?: "0"
                MovieDetailScreen(id = movieId)
            }
        }
    }
}
