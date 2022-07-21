package ru.mingaleev.weatherandroidkotlin.model

import okio.IOException
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather

interface RepositoryDetails {
    fun getWeather(city: City, callback: MyLargeSuperCallback)
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
