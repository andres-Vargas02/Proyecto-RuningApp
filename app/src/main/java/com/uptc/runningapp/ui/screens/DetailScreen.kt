package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, selectedRace: Race) {
    Scaffold(
        topBar = { TopBar("Detalle de Carrera") },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Detalles de la carrera:")
            Text("Distancia: 5 km")
            Text("Duración: 30 min")
            Button(onClick = { navController.navigate("inicio") }) {
                Text("Volver a Inicio")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    //val selectedRace = generateSampleRaces().first()
    //DetailScreen(navController = navController, selectedRace = selectedRace)
}
