package com.example.healthmonitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class MainViewModel(private val healthConnectManager: HealthConnectManager) : ViewModel() {
    private val _steps = MutableStateFlow(0)
    val steps: StateFlow<Int> get() = _steps

    fun loadSteps(startTime: Instant, endTime: Instant) {
        viewModelScope.launch {
            healthConnectManager.readStepsRecord(startTime, endTime)
                .sumOf { it.count }
                .let { _steps.value = it.toInt() }
        }
    }

    fun aboba() {
        steps.value
        loadSteps()
    }
}