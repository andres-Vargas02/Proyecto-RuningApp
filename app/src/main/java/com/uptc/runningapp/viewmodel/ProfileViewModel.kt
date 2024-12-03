package com.uptc.runningapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.data.AppDatabase
import com.uptc.runningapp.data.api.fetchWeather
import com.uptc.runningapp.ui.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ProfileViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    open val uiState: StateFlow<ProfileUiState> = _uiState

    private val _weatherState = MutableStateFlow<String?>(null)
    val weatherState: StateFlow<String?> = _weatherState

    init {
        loadUser()
    }

    fun loadUser() {
        _uiState.value = ProfileUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val userDao = AppDatabase.getDatabase(context).userSessionDao()
                val userId = userDao.getUserId()
                val user = userId?.let { UserRepository.getUserById(it) }
                _uiState.value = ProfileUiState(user = user)
            } catch (e: Exception) {
                println("Error al cargar usuario: ${e.message}")
                _uiState.value = ProfileUiState(error = "Error loading usuario")
            }
        }
    }

    fun loadWeather() {
        viewModelScope.launch {
            val weather = fetchWeather()
            _weatherState.value = weather ?: "No se pudo obtener el clima"
        }
    }

    fun logout() {
        _uiState.value = ProfileUiState(user = null)
    }
}
