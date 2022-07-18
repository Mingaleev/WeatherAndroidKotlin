package ru.mingaleev.weatherandroidkotlin.viewmodel.details

import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO

sealed class AppStateDetails {
    data class Success(val weatherDTO: WeatherDTO) : AppStateDetails()
    data class Error(val error: Throwable) : AppStateDetails()
    object Loading : AppStateDetails()
}