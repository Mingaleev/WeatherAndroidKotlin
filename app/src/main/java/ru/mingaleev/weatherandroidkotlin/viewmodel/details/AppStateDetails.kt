package ru.mingaleev.weatherandroidkotlin.viewmodel.details

import ru.mingaleev.weatherandroidkotlin.domain.Weather

sealed class AppStateDetails {
    data class Success(val weatherData: Weather) : AppStateDetails()
    data class Error(val error: Throwable) : AppStateDetails()
    object Loading : AppStateDetails()
}