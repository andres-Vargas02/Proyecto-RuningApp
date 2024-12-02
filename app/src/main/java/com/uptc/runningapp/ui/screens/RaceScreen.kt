package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceScreen(navController: NavController, races: List<Race>) {
    Scaffold(
        topBar = { TopBar("Registro de Carrera") },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Inicio y detención del seguimiento")
            Button(onClick = { /* lógica para iniciar carrera */ }) {
                Text("Iniciar Carrera")
            }
            Button(onClick = { /* lógica para detener carrera */ }) {
                Text("Detener Carrera")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RaceScreenPreview() {
    //val races = generateSampleRaces()
    //RaceScreen(navController = NavController(LocalContext.current), races = races)
}
