package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.uptc.runningapp.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.platform.LocalContext
import com.uptc.runningapp.data.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Inicio de Sesión") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (email.value.isEmpty() || password.value.isEmpty()) {
                            errorMessage.value = "Por favor, completa todos los campos."
                            return@Button
                        }

                        coroutineScope.launch {
                            isLoading.value = true
                            val isValid = withContext(Dispatchers.IO) {
                                UserRepository.validateLogin(email.value, password.value)
                            }
                            isLoading.value = false

                            if (isValid) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val remoteUser = UserRepository.getUserByEmail(email.value)
                                    if (remoteUser != null) {
                                        val db = AppDatabase.getDatabase(context)
                                        val userSessionDao = db.userSessionDao()

                                        val userSession = UserSession(userId = remoteUser.userId.toIntOrNull() ?: 0)
                                        userSessionDao.insertUserSession(userSession)
                                        withContext(Dispatchers.Main) {
                                            navController.navigate("Inicio")
                                        }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                        }
                                    }
                                }
                            } else {
                                errorMessage.value = "Credenciales incorrectas."
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }
            }
            if (errorMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { navController.navigate("registro") }
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}