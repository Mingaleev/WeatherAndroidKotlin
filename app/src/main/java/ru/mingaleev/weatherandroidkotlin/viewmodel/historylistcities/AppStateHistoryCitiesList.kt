package ru.mingaleev.weatherandroidkotlin.viewmodel.historylistcities

import ru.mingaleev.weatherandroidkotlin.domain.Weather

sealed class AppStateHistoryCitiesList {
    data class SuccessListCity(val weatherListData: List<Weather>) : AppStateHistoryCitiesList()
    data class Error(val error: Throwable) : AppStateHistoryCitiesList()
    object Loading : AppStateHistoryCitiesList()
}