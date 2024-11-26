package com.uptc.runningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.uptc.runningapp.ui.navigation.AppNavHost
import com.uptc.runningapp.ui.composables.generateSampleRaces

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Genera las carreras de muestra
                val sampleRaces = generateSampleRaces()

                // Pasa las carreras generadas a AppNavHost (si es necesario)
                AppNavHost(races = sampleRaces)
            }
        }
    }
}
