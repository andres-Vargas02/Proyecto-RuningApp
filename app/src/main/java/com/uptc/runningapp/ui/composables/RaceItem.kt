package com.uptc.runningapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.R
import com.uptc.runningapp.model.User
import com.uptc.runningapp.model.Race

@SuppressLint("DefaultLocale")
@Composable
fun RaceItem(navController: NavController, race: Race, user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("detalle_carrera/${race.raceId}") },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Column(modifier = Modifier.padding(16.dp)) {
                // Encabezado con nombre y foto de perfil
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = user.profileImage),
                        contentDescription = "Foto de perfil de ${user.name}",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                // Detalles de la carrera
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Fecha: ${race.date}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Distancia: ${String.format("%.1f", race.distance)} km",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Duración: ${formatDuration(race.duration)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        // Ícono de récord personal
                        if (race.isPersonalBest) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_best),
                                contentDescription = "Récord personal",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(5.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = race.photo),
                        contentDescription = "Foto de la carrera del ${race.date}",
                        modifier = Modifier
                            .height(120.dp)
                            .weight(1.5f)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Botones de Like y Share
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { /* Lógica para "Like" */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_like),
                            contentDescription = "Like",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = { /* Lógica para "Share" */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RaceItemPreview() {
    val navController = rememberNavController()
    val race = createSampleRace()
    val user = createSampleUser()

    RaceItem(navController = navController, race = race, user = user)
}

