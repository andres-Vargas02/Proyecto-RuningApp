package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.RaceItem
import com.uptc.runningapp.viewmodel.FeedViewModel

@Composable
fun FeedScreen(navController: NavController, viewModel: FeedViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadRaces()
    }

    Scaffold(
        topBar = { TopBar("Inicio") },
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        when {
            uiState.value.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            uiState.value.error != null -> {
                Text(
                    text = uiState.value.error ?: "Unknown Error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(uiState.value.races) { race ->
                        RaceItem(navController = navController, race = race)
                        HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
