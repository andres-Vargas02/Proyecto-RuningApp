package com.uptc.runningapp.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.uptc.runningapp.data.AppDatabase
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.repositories.RaceRepository
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceScreen(navController: NavController, profileViewModel: ProfileViewModel) {
    val context = LocalContext.current

    val uiState = profileViewModel.uiState.collectAsState()

    val userId = uiState.value.user?.userId

    // State variables
    var raceName by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val profileImageBase64 = remember { mutableStateOf<String?>(null) }

    val calendar = Calendar.getInstance()
    val coroutineScope = rememberCoroutineScope()

    val weather by profileViewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadWeather()
    }

    // Date Picker
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUri ->
            val base64String = convertImageToBase64(imageUri, context)
            profileImageBase64.value = base64String
        }
    }

    Scaffold(
        topBar = { TopBar("Registro de Carrera") },
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            weather?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Race Name
            TextField(
                value = raceName,
                onValueChange = { raceName = it },
                label = { Text("Nombre de la Carrera") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Distance
            TextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distancia (km)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Duration
            TextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duración (segundos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date
            Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = if (date.isNotEmpty()) "Fecha: $date" else "Seleccionar Fecha")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Image
            if (profileImageBase64.value != null) {
                val imageBytes = Base64.decode(profileImageBase64.value, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen de Perfil",
                    modifier = Modifier.size(120.dp)
                )
            }

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar Imagen de Perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))
            val userIdInt = try {
                userId?.toInt()
            } catch (e: NumberFormatException) {
                null
            }
            // Save Button
            Button(
                onClick = {
                    if (raceName.isNotEmpty() && distance.isNotEmpty() && duration.isNotEmpty() && date.isNotEmpty()) {
                        try {
                            val race = userIdInt?.let {
                                Race(
                                    raceId = 0,
                                    userId = it,
                                    raceName = raceName,
                                    distance = distance.toFloat(),
                                    duration = duration.toLong(),
                                    date = date,
                                    profileImage = profileImageBase64.value
                                )
                            }

                            coroutineScope.launch {
                                val isSaved = race?.let { RaceRepository.addRace(it) }
                                if (isSaved == true) {
                                    Toast.makeText(context, "Carrera guardada exitosamente", Toast.LENGTH_SHORT).show()
                                    navController.navigate("inicio")
                                } else {
                                    Toast.makeText(context, "Error al guardar la carrera", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }

                    } else {
                        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Carrera")
            }
        }
    }
}

