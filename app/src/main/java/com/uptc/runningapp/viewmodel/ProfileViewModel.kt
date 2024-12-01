package com.uptc.runningapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.ui.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    open val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUser()
    }

    private fun loadUser() {
        _uiState.value = ProfileUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val user = UserRepository.getUserById(1)
                _uiState.value = ProfileUiState(user = user)
            } catch (e: Exception) {
                println("Error al cargar carreras: ${e.message}")
                _uiState.value = ProfileUiState(error = "Error loading races")
            }
        }
    }
}
