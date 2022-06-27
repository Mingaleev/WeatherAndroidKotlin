package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.Weather

interface RepositoryListCity {
    fun getListWeather(location: Location): List<Weather>
}
interface RepositoryCity {
    fun getWeather(lat: Double, lon: Double): Weather
}

sealed class Location{
    object Russian: Location()
    object World: Location()
}
