package com.uptc.runningapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.ui.composables.BottomBar
import com.uptc.runningapp.ui.composables.TopBar
import com.uptc.runningapp.ui.composables.RaceItem
import com.uptc.runningapp.ui.composables.generateSampleRaces

@Composable
fun FeedScreen(navController: NavController, races: List<Race>) {
    Scaffold(
        topBar = { TopBar("Inicio") },
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(races) { race ->
                RaceItem(navController = navController, race = race)
                HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val fakeNavController = rememberNavController()
    val sampleRaces = generateSampleRaces()
    FeedScreen(navController = fakeNavController, races = sampleRaces)
}
