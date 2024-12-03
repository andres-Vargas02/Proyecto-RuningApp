package com.uptc.runningapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.repositories.RaceRepository
import com.uptc.runningapp.ui.state.RaceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RaceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RaceUiState())
    val uiState: StateFlow<RaceUiState> = _uiState

    fun loadRaceById(raceId: Int) {
        _uiState.value = RaceUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val race = RaceRepository.getRaceById(raceId)
                _uiState.value = RaceUiState(race = race)
            } catch (e: Exception) {
                println("Error al cargar carrera: ${e.message}")
                _uiState.value = RaceUiState(error = "Error loading race")
            }
        }
    }
}
