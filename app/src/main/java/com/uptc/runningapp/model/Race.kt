package com.uptc.runningapp.model

data class Race(
    val raceId: Int,
    val userId: Int,
    val raceName: String,
    val distance: Float,
    val duration: Long,
    val date: String,
    val locations: List<Location> = listOf()
)
