package com.uptc.runningapp.ui.state

import com.uptc.runningapp.model.User
import java.util.Date

data class ProfileUiState(
    val user: User? = User(
        userId = "defaultId",
        name = "Default User",
        email = "default@example.com",
        profileImage = "",
        createAt = Date()
    ),
    val isLoading: Boolean = false,
    val error: String? = null
)
