package com.uptc.runningapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(title = { Text(title) })
}

@Composable
fun BottomBar(navController: NavController) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp)
        ) {
            BottomBarItem(navController, "inicio", R.drawable.ic_home, "Inicio")
            BottomBarItem(navController, "perfil", R.drawable.ic_profile, "Perfil")
            BottomBarItem(navController, "registro_carrera", R.drawable.ic_race, "Registrar Carrera")
        }
    }
}

@Composable
private fun BottomBarItem(
    navController: NavController,
    route: String,
    icon: Int,
    description: String
) {
    IconButton(onClick = { navController.navigate(route) }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = description,
            modifier = Modifier.size(24.dp)
        )
    }
}


@SuppressLint("DefaultLocale")
fun format(duration: Long): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val seconds = duration % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}
