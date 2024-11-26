package com.uptc.runningapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.R
import com.uptc.runningapp.model.Race

/**
 * Representa un ítem de carrera en la lista de carreras.
 *
 * @param navController Controlador de navegación.
 * @param race Objeto de tipo Race con información de la carrera.
 */
@Composable
fun RaceItem(navController: NavController, race: Race) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("raceDetails/${race.raceId}") },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        ConstraintLayout(modifier = Modifier.padding(8.dp)) {
            val (icon, raceInfo) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_race), // Asegúrate de que este recurso exista
                contentDescription = "Race Icon",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .constrainAs(raceInfo) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                    }
            ) {
                Text(
                    text = "Fecha: ${race.date}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Distancia: ${race.distance} km",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Duración: ${formatDuration(race.duration)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Formatea la duración (en segundos) en una cadena legible (HH:mm:ss).
 *
 * @param duration Duración en segundos.
 * @return Cadena formateada.
 */
@SuppressLint("DefaultLocale")
fun formatDuration(duration: Long): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val seconds = duration % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun RaceItemPreview() {
    val navController = rememberNavController()
    val sampleRace = Race(
        raceId = "1",
        distance = 10.5f,
        duration = 3600,
        date = "2023-11-25"
    )
    RaceItem(navController = navController, race = sampleRace)
}
