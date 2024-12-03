package com.uptc.runningapp.ui.screens

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.formatDuration



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceScreen(navController: NavController) {
    val distances = listOf(1, 5, 10, 15, 20, 21)
    var expanded by remember { mutableStateOf(false) }
    var selectedDistance by remember { mutableStateOf(distances[0]) }

    var raceTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    var raceFinished by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    fun startRace() {
        isRunning = true
        raceFinished = false
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                raceTime++
            }

            override fun onFinish() {
                raceFinished = true
            }
        }
        timer?.start()
    }

    fun stopRace() {
        isRunning = false
        raceFinished = true
        timer?.cancel()
    }

    fun saveData() {
        raceFinished = false
        showDialog = true
    }

    fun cancelSave() {
        raceFinished = false
        raceTime = 0L
        navController.popBackStack()
    }

    Scaffold(
        topBar = { TopBar("Registro de Carrera") },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            RaceHeader(selectedDistance, raceTime)
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { if (!isRunning) expanded = !expanded }
            ) {
                TextField(
                    value = "$selectedDistance km",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Distancia") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    },
                    enabled = !isRunning,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    distances.forEach { distance ->
                        DropdownMenuItem(
                            text = { Text("$distance km") },
                            onClick = {
                                selectedDistance = distance
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isRunning) {
                        stopRace()
                    } else {
                        startRace()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isRunning) "Detener Carrera" else "Iniciar Carrera")
            }

            if (raceFinished) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { saveData() }) {
                        Text("Guardar")
                    }
                    Button(onClick = { cancelSave() }) {
                        Text("Cancelar")
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Datos de la Carrera") },
                text = {
                    Column {
                        Text("Distancia: $selectedDistance km")
                        Text("Tiempo: ${formatDuration(raceTime)}")
                    }
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

@Composable
fun RaceHeader(distance: Int, time: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Distancia: $distance km",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tiempo: ${formatDuration(time)}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}
