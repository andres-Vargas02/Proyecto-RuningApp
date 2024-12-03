package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.R
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.model.User
import com.uptc.runningapp.ui.composables.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, selectedRace: Race, user: User) {
    val userProfileImage = user.profileImage

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = userProfileImage),
                    contentDescription = "Foto de perfil de ${user.name}",
                    modifier = Modifier
                        .weight(1f)
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                if (selectedRace.isPersonalBest) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_best),
                        contentDescription = "Récord personal",
                        modifier = Modifier
                            .weight(1f)
                            .size(35.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Fecha: ${selectedRace.date}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = selectedRace.photo),
                contentDescription = "Imagen de carrera",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Distancia: ${selectedRace.distance} km", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Text("Duración: ${formatDuration(selectedRace.duration)}", style = MaterialTheme.typography.bodyMedium)

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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    val race = createSampleRace()
    val user = createSampleUser()

    DetailScreen(navController = navController, selectedRace = race, user = user)
}
