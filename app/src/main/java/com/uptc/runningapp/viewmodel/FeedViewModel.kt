package com.uptc.runningapp.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.repositories.RaceRepository
import com.uptc.runningapp.ui.state.FeedUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState

    init {
        loadRaces()
    }

    private fun loadRaces() {
        _uiState.value = FeedUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val races = RaceRepository.getAllRaces()
                _uiState.value = FeedUiState(races = races)
            } catch (e: Exception) {
                println("Error al cargar carreras: ${e.message}")
                _uiState.value = FeedUiState(error = "Error loading races")
            }
        }
    }
}
