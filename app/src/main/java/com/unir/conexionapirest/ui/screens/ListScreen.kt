package com.unir.conexionapirest.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.unir.conexionapirest.data.model.ItemResumen
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.ErrorSnackBar
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.components.SearchField
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.theme.TextoApp
import com.unir.conexionapirest.ui.viewmodels.ViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(){
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // CONTENIDO DE LA PANTALLA
        Box(Modifier.fillMaxSize()){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header()
                Text(
                    text = TextoApp.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    color = MiPaletaDeColores.Gold,
                    maxLines = 3,
                )
                ItemList(
                    Modifier.fillMaxHeight()
                )
            }
            ErrorSnackBar()
        }
    }
}


@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = hiltViewModel()

) {
    viewModel.fetchAll()
    val itemsList by viewModel.filteredList.collectAsState()

    SearchField()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsList.let {
            if (it.isEmpty()) {
                item {
                    Text("No hay resultados de búsqueda")
                }
            } else {
                items(it.size) { index ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        ItemResumen(item = it[index])
                    }
                    CustomHorizontalDivider()

                }
            }
        }

    }
}



@Composable
fun ItemResumen(
    item: ItemResumen,
) {
    val navigationViewModel = LocalNavigationViewModel.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // IMAGEN A LA IZQUIERDA
        AsyncImage(
            model = item.image.replace("http", "https"),
            contentDescription = item.name,
            modifier = Modifier
                .height(200.dp)
                .weight(1f) // Toma la mitad del ancho disponible
        )

        // Texto en la segunda mitad
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = 18.dp)
        ) {

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(){
                DetailButton(onClick = { navigationViewModel.navigate(ScreensRoutes.DetailScreen.createRoute(id = item.id)) })

                Spacer(modifier = Modifier.height(15.dp).width(15.dp))

                Text(
                    text = "${TextoApp.campo_3} : ${ item.price.toString() }",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
