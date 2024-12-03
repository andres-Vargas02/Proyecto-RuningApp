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
import com.uptc.runningapp.model.User
import com.uptc.runningapp.ui.composables.*

@Composable
fun FeedScreen(navController: NavController, races: List<Pair<Race, User>>) {
    Scaffold(
        topBar = { TopBar("Inicio") },
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(races) { (race, user) ->
                RaceItem(navController = navController, race = race, user = user)
                HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val navController = rememberNavController()
    val races = generateSampleRaces()
    FeedScreen(navController = navController, races = races)
}
