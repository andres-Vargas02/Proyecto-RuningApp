package com.uptc.runningapp.ui.state

import com.uptc.runningapp.model.Race

data class FeedUiState(
    val races: List<Race> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
