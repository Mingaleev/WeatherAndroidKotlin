package ru.mingaleev.weatherandroidkotlin.model

import okio.IOException
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather

interface RepositoryWeatherByLocation {
    fun getWeather(city: City, callback: MySuperCallbackCity)
}

interface RepositoryHistoryCitiesList {
    fun getHistoryListWeather(callbackListCities: MySuperCallbackListCities)
}

interface RepositoryAddWeatherToDB {
    fun addWeather(weather: Weather)
}

interface MySuperCallbackCity {
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}

interface MySuperCallbackListCities {
    fun onResponse(weather: List<Weather>)
    fun onFailure(e: IOException)
}

interface RepositoryCitiesList {
    fun getListWeather(location: Location): List<Weather>
}

sealed class Location {
    object Russian : Location()
    object World : Location()
}
