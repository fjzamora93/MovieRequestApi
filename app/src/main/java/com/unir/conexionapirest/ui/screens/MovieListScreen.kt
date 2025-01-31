package com.unir.conexionapirest.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.MovieResumen
import com.unir.conexionapirest.data.model.SearchFilter
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.components.BookMarkButton
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.ErrorSnackBar
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.components.SearchField
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        LeftHalfDrawer(
            drawerState = drawerState,
            onClose = {
                coroutineScope.launch { drawerState.close() }
            }
        ) {
            // CONTENIDO DE LA PANTALLA
            Box(Modifier.fillMaxSize()){

                Column {

                    Header(
                        onMenuClick = {
                            coroutineScope.launch { drawerState.open() }
                        },
                    )
                    Text(
                        text = "Lista de películas",
                        style = MaterialTheme.typography.titleLarge,
                        color = MiPaletaDeColores.Gold,
                        maxLines = 3,
                    )

                    MovieList(
                        Modifier.fillMaxHeight()
                    )
                }
                ErrorSnackBar()

            }

        }
    }
}


@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel()

) {
    movieViewModel.fetchMovies()

    val movies by movieViewModel.movies.observeAsState(emptyList())
    var filter by remember { mutableStateOf(SearchFilter()) }

    // BARRA DE BÚSQUEDA
    SearchField(
        onSearch = { searchText ->
            filter = filter.copy(title = searchText)
            movieViewModel.fetchMovies(filter = filter)
        }
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (movies.isNotEmpty()) {
            items(movies) { movie ->
                MovieItem(movie)
            }
        } else {
            item {
                Text("No hay resultados de búsqueda")
            }
        }
    }
}




@Composable
fun MovieItem(
    movie: MovieResumen,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val navigationViewModel = LocalNavigationViewModel.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
    ) {
        // Póster en la primera mitad
        AsyncImage(
            model = movie.poster, // URL de la imagen
            contentDescription = movie.title,
            modifier = Modifier
                .height(200.dp)
                .weight(1f) // Toma la mitad del ancho disponible
        )

        // Texto en la segunda mitad
        Column(
            modifier = Modifier
                .weight(1f) // Toma el doble del espacio que el póster
                .fillMaxHeight() // Asegura que ocupe toda la altura disponible
                .padding(top = 18.dp)
        ) {

            Row(){
                Text(
                    text = movie.title ?: "Título no disponible",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                )
            }

            Text(
                text = movie.year ?: "Año desconocido",
                style = MaterialTheme.typography.bodyLarge
            )

            Row(){
                DetailButton(
                    onClick = {
                        navigationViewModel.navigate(
                            ScreensRoutes.MovieDetailScreen.createRoute(movieID = movie.imdbID)
                        )

                    }
                )

                BookMarkButton (
                    onClick = {
                        println("Añadir a favoritos ${movie.title}")
                        movieViewModel.addMovieToFavorites(movie)
                    },

                )
            }

        }

    }
    CustomHorizontalDivider()
}
