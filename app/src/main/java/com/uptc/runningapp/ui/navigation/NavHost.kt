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
fun AppNavHost(races: List<Race>) { // Se agrega el parámetro 'races' en AppNavHost
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { SignUpScreen(navController) }
        composable("inicio") { FeedScreen(navController, races) } // Se pasa 'races' aquí
        composable("perfil") { ProfileScreen(navController, races) }
        composable("registro_carrera") { RaceScreen(navController, races) } // Se pasa 'races' aquí
        composable("detalle_carrera/{raceId}") { backStackEntry ->
            val raceId = backStackEntry.arguments?.getString("raceId") // Obtiene el ID de la carrera
            val selectedRace = races.find { it.raceId == raceId } // Busca la carrera en la lista
            if (selectedRace != null) {
                DetailScreen(navController, selectedRace) // Pasa la carrera seleccionada
            } else {
                // Maneja el caso de error (por ejemplo, muestra un mensaje)
                Text("Carrera no encontrada")
            }
        }
    }
}

