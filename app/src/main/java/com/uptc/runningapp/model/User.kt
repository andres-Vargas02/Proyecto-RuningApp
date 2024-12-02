package com.uptc.runningapp.model

import java.util.Date

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val profileImage: String?,
    val createAt: Date,
    val achievements: List<Achievement> = listOf(),
    val races: List<Race> = listOf()
)
