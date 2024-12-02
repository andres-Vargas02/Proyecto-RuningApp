package com.uptc.runningapp.ui.screens

import UserRepository
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.uptc.runningapp.data.AppDatabase
import com.uptc.runningapp.data.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val successMessage = remember { mutableStateOf("") }
    val profileImageBase64 = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUri ->
            val base64String = convertImageToBase64(imageUri, context)
            profileImageBase64.value = base64String
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Usuario") })
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
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
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
            TextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (profileImageBase64.value != null) {
                val imageBytes = Base64.decode(profileImageBase64.value, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Profile Image", modifier = Modifier.size(100.dp))
            }

            Button(
                onClick = {
                    getContent.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar Foto de Perfil")
            }

            Spacer(modifier = Modifier.height(16.dp))



            if (isLoading.value) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (name.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()) {
                            errorMessage.value = "Por favor, completa todos los campos."
                            return@Button
                        }

                        if (password.value != confirmPassword.value) {
                            errorMessage.value = "Las contraseñas no coinciden."
                            return@Button
                        }

                        coroutineScope.launch {
                            isLoading.value = true
                            errorMessage.value = ""
                            successMessage.value = ""

                            val isRegistered = withContext(Dispatchers.IO) {
                                val profileImage = profileImageBase64.value
                                profileImageBase64.value?.let {
                                    if (profileImage != null) {
                                        UserRepository.registerUser(
                                            name = name.value,
                                            email = email.value,
                                            password = password.value,
                                            profileImage = profileImage
                                        )
                                    } else {
                                        UserRepository.registerUser(
                                            name = name.value,
                                            email = email.value,
                                            password = password.value,
                                            profileImage = ""
                                        )
                                    }
                                }
                            }

                            isLoading.value = false

                            if (isRegistered == true) {
                                val db = AppDatabase.getDatabase(context)
                                val userSessionDao = db.userSessionDao()

                                val userSession = UserSession(userId = UserRepository.getUserByEmail(email.value)?.userId?.toIntOrNull() ?: 0)
                                userSessionDao.insertUserSession(userSession)
                                successMessage.value = "Registro exitoso. ¡Ahora puedes iniciar sesión!"
                                navController.navigate("inicio")
                            } else {
                                errorMessage.value = "No se pudo registrar el usuario. Intenta nuevamente."
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }
            }
            if (errorMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage.value,
                    color = MaterialTheme.colorScheme.error
                )
            }
            if (successMessage.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = successMessage.value,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { navController.navigate("login") }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión aquí")
            }
        }
    }
}

fun convertImageToBase64(imageUri: android.net.Uri, context: Context): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
