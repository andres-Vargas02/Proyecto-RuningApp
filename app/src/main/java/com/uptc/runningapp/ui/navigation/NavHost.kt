package com.uptc.runningapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.ui.screens.DetailScreen
import com.uptc.runningapp.ui.screens.FeedScreen
import com.uptc.runningapp.ui.screens.LoginScreen
import com.uptc.runningapp.ui.screens.ProfileScreen
import com.uptc.runningapp.ui.screens.RaceScreen
import com.uptc.runningapp.ui.screens.SignUpScreen

@Composable
fun AppNavHost(races: List<Race>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { SignUpScreen(navController) }
        composable("inicio") { FeedScreen(navController, races) }
        composable("perfil") { ProfileScreen(navController, races) }
        composable("registro_carrera") { RaceScreen(navController, races) }
        composable("detalle_carrera/{raceId}") { backStackEntry ->
            val raceId = backStackEntry.arguments?.getString("raceId")
            val selectedRace = races.find { it.raceId == raceId }
            if (selectedRace != null) {
                DetailScreen(navController, selectedRace)
            } else {

                Text("Carrera no encontrada")
            }
        }
    }
}

