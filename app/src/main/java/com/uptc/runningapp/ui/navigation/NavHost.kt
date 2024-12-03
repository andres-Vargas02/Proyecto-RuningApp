package com.uptc.runningapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.model.User
import com.uptc.runningapp.ui.composables.ErrorScreen
import com.uptc.runningapp.ui.screens.*

@Composable
fun AppNavHost(
    races: List<Pair<Race, User>>,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { SignUpScreen(navController) }
        composable("inicio") { FeedScreen(navController, races) }
        composable("perfil") { ProfileScreen(navController, races) }
        composable("registro_carrera") { RaceScreen(navController) }
        composable("detalle_carrera/{raceId}") { backStackEntry ->
            val raceId = backStackEntry.arguments?.getString("raceId")
            val selectedRace = races.find { it.first.raceId == raceId }?.first

        }
    }
}


