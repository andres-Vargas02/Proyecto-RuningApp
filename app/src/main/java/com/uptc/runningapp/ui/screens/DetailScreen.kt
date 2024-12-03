package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.model.User
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.format
@Composable
fun DetailScreen(navController: NavController, selectedRace: Race) {
    Scaffold(
        topBar = { TopBar("Detalle de Carrera") },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            RaceDetails(selectedRace = selectedRace)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("inicio") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver a Inicio")
            }
        }
    }
}


@Composable
fun RaceDetails(selectedRace: Race) {
    Column {
        Text("Fecha: ${selectedRace.date}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Distancia: ${selectedRace.distance} km", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Duración: ${format(selectedRace.duration)}", style = MaterialTheme.typography.bodyMedium)
    }
}
