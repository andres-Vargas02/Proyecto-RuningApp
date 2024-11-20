package com.uptc.runningapp.model

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val achievements: List<Achievement> = listOf(),
    val races: List<Race> = listOf()
)
