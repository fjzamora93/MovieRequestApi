package com.unir.conexionapirest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.ViewModel

@Composable
fun ErrorSnackBar(
    viewModel: ViewModel = hiltViewModel(),
){
    val errorState by viewModel.error.observeAsState(false)
    if (errorState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Snackbar(
                modifier = Modifier.padding(16.dp).background(Color.Red),
                action = {
                    IconButton(onClick = { viewModel.clearError() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar mensaje de error"
                        )
                    }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Ícono de advertencia",
                        tint = MiPaletaDeColores.Gold,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onError,
                        text= "Simulación de un error. En caso de que suceda algún error durante" +
                                "el fetch a la API, se disparará este SnackBar simulando un mensaje de error." +
                                "QUeda pendiente manejor los errores no solo al hacer peticiones a la API, " +
                                "sino también en el tratamiento de la base de datos local."
                    )
                }

            }

        }

    }
}