package ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist

import ru.mingaleev.weatherandroidkotlin.domain.Weather

sealed class AppStateWeatherList {
    data class SuccessListCity(val weatherListData: List<Weather>) : AppStateWeatherList()
    data class Error(val error: Throwable) : AppStateWeatherList()
    object Loading : AppStateWeatherList()
}