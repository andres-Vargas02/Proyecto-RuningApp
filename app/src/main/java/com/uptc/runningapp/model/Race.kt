package com.uptc.runningapp.model

data class Race(
    val raceId: String,
    val distance: Float,
    val duration: Long,
    val date: String,
    val photo: Int,
    val locations: List<Location> = listOf(),
    val isPersonalBest: Boolean = false
)

