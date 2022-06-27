package ru.mingaleev.weatherandroidkotlin.viewmodel

import ru.mingaleev.weatherandroidkotlin.domain.Weather

sealed class AppState {
    data class SuccessCity(val weatherData: Weather) : AppState()
    data class SuccessListCity(val weatherListData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}