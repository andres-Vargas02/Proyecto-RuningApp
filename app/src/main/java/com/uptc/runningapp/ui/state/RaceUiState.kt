package com.uptc.runningapp.ui.state

import com.uptc.runningapp.model.Race

/**
 * Representa el estado de la interfaz de usuario para las operaciones relacionadas con una carrera específica.
 */
data class RaceUiState(
    val race: Race? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
