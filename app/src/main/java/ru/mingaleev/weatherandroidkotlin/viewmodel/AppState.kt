package ru.mingaleev.weatherandroidkotlin.viewmodel

import ru.mingaleev.weatherandroidkotlin.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}