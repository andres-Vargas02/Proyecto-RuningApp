package com.uptc.runningapp.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.model.User
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import android.util.Base64
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uptc.runningapp.ui.state.ProfileUiState
import com.uptc.runningapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val user = uiState.value.user

    Scaffold(
        topBar = { TopBar("Perfil") },
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (user != null) {
                ProfileHeader(user)
                Spacer(modifier = Modifier.height(16.dp))
                UserInfo(user)
            } else {
                Text(text = "Cargando perfil...", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ProfileHeader(user: User) {
    val base64Image = user.profileImage?: ""
    val bitmap = remember(base64Image) { decodeBase64ToBitmap(base64Image) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "User Image",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun UserInfo(user: User) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nombre: ${user.name}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Correo: ${user.email}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Fecha de creación: ${user.createAt}", style = MaterialTheme.typography.bodyMedium)
    }
}

fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    return try {
        val cleanBase64 = base64String.replace("data:image/[^;]*;base64,".toRegex(), "")
        val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}