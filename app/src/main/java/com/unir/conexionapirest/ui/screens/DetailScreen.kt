package com.unir.conexionapirest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.theme.TextoApp
import com.unir.conexionapirest.ui.theme.Typography
import com.unir.conexionapirest.ui.viewmodels.ViewModel


@Composable
fun MovieDetailScreen(
    id: String,
    viewModel: ViewModel = hiltViewModel()
) {

    viewModel.fetchById(id)
    LaunchedEffect(id) {
        viewModel.fetchById(id)
    }

    val itemDetails by viewModel.selectedItem.collectAsState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Header()


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (itemDetails != null) {
                        Text(
                            text = itemDetails?.name ?: "No Title",
                            style = MaterialTheme.typography.titleLarge,
                            color = MiPaletaDeColores.Gold,
                            maxLines = 3,
                        )

                        AsyncImage(
                            model = itemDetails!!.image.replace("http", "https"),
                            contentDescription = itemDetails?.name ?: "img no disponible",
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth()
                                .padding(20.dp)
                        )
                        Text(
                            text = TextoApp.campo_1,
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = itemDetails!!.price.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        Text(
                            text = TextoApp.campo_2,
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = itemDetails!!.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        Text(
                            text = TextoApp.campo_3,
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = itemDetails!!.category,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )

                    } else {
                        Column {
                            Text("Cargando detalles...")
                        }
                    }
                }
            }
        }
    }
}