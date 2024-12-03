package com.uptc.runningapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.ui.screens.DetailScreen
import com.uptc.runningapp.ui.screens.FeedScreen
import com.uptc.runningapp.ui.screens.LoginScreen
import com.uptc.runningapp.ui.screens.ProfileScreen
import com.uptc.runningapp.ui.screens.RaceScreen
import com.uptc.runningapp.ui.screens.SignUpScreen
import com.uptc.runningapp.viewmodel.FeedViewModel
import com.uptc.runningapp.viewmodel.ProfileViewModel
import com.uptc.runningapp.viewmodel.ProfileViewModelFactory

@Composable
fun AppNavHost(races: List<Race>) {
    val navController = rememberNavController()
    val feedViewModel: FeedViewModel = viewModel()
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(context)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { SignUpScreen(navController) }
        composable("inicio") { FeedScreen(navController, viewModel = feedViewModel) }
        composable("perfil") { ProfileScreen(navController, viewModel = profileViewModel)}
        composable("registro_carrera") { RaceScreen(navController, profileViewModel) }
        composable("detalle_carrera/{raceId}") { backStackEntry ->
            val raceId = backStackEntry.arguments?.getString("raceId")
            //val selectedRace = feedViewModel.uiState.value.races.find { it.raceId == raceId }
            //val selectedRace = races.find { it.raceId == raceId }
            val selectedRace = 1
            if (selectedRace != null) {
                //DetailScreen(navController, selectedRace)
            } else {
                Text("Carrera no encontrada")
            }
        }
    }
}
