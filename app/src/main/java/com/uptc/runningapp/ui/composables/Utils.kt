package com.uptc.runningapp.ui.composables

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uptc.runningapp.R
import com.uptc.runningapp.model.Location
import com.uptc.runningapp.model.Race

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = { Text(title) }
    )
}

@Composable
fun BottomBar(navController: NavController) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(
                onClick = { navController.navigate("inicio") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Inicio",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("perfil") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("registro_carrera") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_race),
                    contentDescription = "Registrar Carrera",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}