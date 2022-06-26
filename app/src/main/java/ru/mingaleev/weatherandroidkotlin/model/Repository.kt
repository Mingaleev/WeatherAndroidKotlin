package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.Weather

interface Repository {
    fun getListWeather(): List<Weather>
    fun getWeather(lat: Double, lon: Double): Weather
}
