package ru.mingaleev.weatherandroidkotlin.model

import okio.IOException
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather

interface RepositoryWeatherByLocation {
    fun getWeather(city: City, callback: MyLargeSuperCallback)
}

interface RepositoryAddWeatherToDB {
    fun addWeather(weather: Weather)
}

interface MyLargeSuperCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}

interface RepositoryCitiesList {
    fun getListWeather(location: Location): List<Weather>
}

sealed class Location {
    object Russian : Location()
    object World : Location()
}
