package com.uptc.runningapp.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.R
import com.uptc.runningapp.model.Location
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(title = { Text(title) })
}

@Composable
fun BottomBar(navController: NavController) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp)
        ) {
            BottomBarItem(navController, "inicio", R.drawable.ic_home, "Inicio")
            BottomBarItem(navController, "perfil", R.drawable.ic_profile, "Perfil")
            BottomBarItem(navController, "registro_carrera", R.drawable.ic_race, "Registrar Carrera")
        }
    }
}

@Composable
private fun BottomBarItem(
    navController: NavController,
    route: String,
    icon: Int,
    description: String
) {
    IconButton(onClick = { navController.navigate(route) }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = description,
            modifier = Modifier.size(24.dp)
        )
    }
}

fun createSampleRace(
    raceId: String = "1",
    distance: Float = 5.0f,
    duration: Long = 1800L,
    date: String = "2024-01-01",
    photo: Int = R.drawable.ic_race,
    locations: List<Location> = listOf(),
    isPersonalBest: Boolean = true
): Race {
    return Race(
        raceId = raceId,
        distance = distance,
        duration = duration,
        date = date,
        photo = photo,
        locations = locations,
        isPersonalBest = isPersonalBest
    )
}

fun createSampleUser(
    userId: String = "123",
    name: String = "Juanito",
    email: String = "juanito@example.com",
    profileImage: Int = R.drawable.ic_profile
): User {
    return User(
        userId = userId,
        name = name,
        email = email,
        profileImage = profileImage
    )
}

fun generateSampleRaces(): List<Pair<Race, User>> {
    val sampleUser = createSampleUser()

    return listOf(
        createSampleRace(
            raceId = "1",
            distance = 5.0f,
            duration = 1800L,
            locations = listOf(
                Location(latitude = 5.057, longitude = -73.345),
                Location(latitude = 5.058, longitude = -73.346)
            )
        ) to sampleUser,
        createSampleRace(
            raceId = "2",
            distance = 10.0f,
            duration = 3600L,
            date = "2024-02-01",
            locations = listOf(
                Location(latitude = 5.059, longitude = -73.347),
                Location(latitude = 5.060, longitude = -73.348)
            )
        ) to sampleUser,
        createSampleRace(
            raceId = "3",
            distance = 21.1f,
            duration = 7200L,
            date = "2024-03-01",
            locations = listOf(
                Location(latitude = 5.061, longitude = -73.349),
                Location(latitude = 5.062, longitude = -73.350)
            )
        ) to sampleUser
    )
}

@SuppressLint("DefaultLocale")
fun formatDuration(duration: Long): String {
    val hours = duration / 3600
    val minutes = (duration % 3600) / 60
    val seconds = duration % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}
