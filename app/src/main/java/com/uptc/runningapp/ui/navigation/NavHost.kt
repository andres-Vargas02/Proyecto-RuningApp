package com.uptc.runningapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.ui.screens.DetailScreen
import com.uptc.runningapp.ui.screens.FeedScreen
import com.uptc.runningapp.ui.screens.LoginScreen
import com.uptc.runningapp.ui.screens.ProfileScreen
import com.uptc.runningapp.ui.screens.RaceScreen
import com.uptc.runningapp.ui.screens.SignUpScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { SignUpScreen(navController) }
        composable("inicio") { FeedScreen(navController) }
        composable("perfil") { ProfileScreen(navController) }
        composable("registro_carrera") { RaceScreen(navController) }
        composable("detalle_carrera") { DetailScreen(navController) }
    }

}
