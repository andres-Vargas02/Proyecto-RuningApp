package com.uptc.runningapp.ui.state

import com.uptc.runningapp.model.Race

data class DetailUiState(
    val race: Race? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
