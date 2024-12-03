package com.uptc.runningapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.repositories.RaceRepository
import com.uptc.runningapp.ui.state.DetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun loadRaceDetails(raceId: Int) { // raceId es Int
        _uiState.value = DetailUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val race = RaceRepository.getRaceById(raceId) // Ahora acepta Int
                _uiState.value = DetailUiState(race = race)
            } catch (e: Exception) {
                println("Error al cargar detalles de la carrera: ${e.message}")
                _uiState.value = DetailUiState(error = "Error loading race details")
            }
        }
    }

    fun clearState() {
        _uiState.value = DetailUiState()
    }
}
