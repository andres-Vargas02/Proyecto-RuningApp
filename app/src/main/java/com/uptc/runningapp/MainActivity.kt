package com.uptc.runningapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.uptc.runningapp.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AppNavHost(races = listOf())
            }
        }
    }
}


