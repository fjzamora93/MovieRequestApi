package com.unir.conexionapirest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.theme.ButtonsModifier
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.ViewModel

@Composable
fun CustomHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MiPaletaDeColores.Bronze,
    thickness: Dp = 4.dp,
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}


@Composable
fun DetailButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = ButtonsModifier.regularIcon
    ) {
        Icon(
            imageVector = Icons.Default.RemoveRedEye,
            contentDescription = "View Details",
            tint = MiPaletaDeColores.LeatherAged
        )
    }
}


@Composable
fun SearchField(
    viewModel : ViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.filter(searchText)
            },
            label = { Text("Search") },
            modifier = Modifier
                .weight(5f)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MiPaletaDeColores.Bronze),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MiPaletaDeColores.Gold
                )
            },
            singleLine = true
        )

        ClearButton(
            modifier = Modifier.weight(1f),
            onClick = {
                searchText = ""
                viewModel.filter(searchText)
            }
        )
    }

}



@Composable
fun ClearButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        modifier = ButtonsModifier.navigationIcon,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear",
            tint = MiPaletaDeColores.BloodRed
        )
    }
}




@Composable
fun HomeButton() {
    val navigationViewModel = LocalNavigationViewModel.current

    IconButton(
        onClick = { navigationViewModel.navigate(ScreensRoutes.MainScreen.route) },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MiPaletaDeColores.Bronze)
            .padding(8.dp)

    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Go Back",
            tint = MiPaletaDeColores.ParchmentDark,
            modifier = Modifier.size(24.dp)
        )
    }
}
