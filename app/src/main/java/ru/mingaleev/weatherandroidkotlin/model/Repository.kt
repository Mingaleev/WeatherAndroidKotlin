package ru.mingaleev.weatherandroidkotlin.model

import okio.IOException
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO

interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback)
}

interface MyLargeSuperCallback {
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: IOException)
}

interface RepositoryCitiesList {
    fun getListWeather(location: Location): List<Weather>
}

sealed class Location {
    object Russian : Location()
    object World : Location()
}
