package com.uptc.runningapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uptc.runningapp.model.Race
import com.uptc.runningapp.repositories.RaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(private val repository: RaceRepository) : ViewModel() {

    private val _races = MutableStateFlow<List<Race>>(emptyList())
    val races: StateFlow<List<Race>> get() = _races

    init {
        loadRaces()
    }

    private fun loadRaces() {
        viewModelScope.launch {
            val raceList = repository.getAllRaces()
            _races.value = raceList
        }
    }
}

class RaceViewModelFactory(private val repository: RaceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RaceViewModel::class.java)) {
            return RaceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}