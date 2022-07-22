package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.getRussianCities
import ru.mingaleev.weatherandroidkotlin.domain.getWorldCities

class RepositoryWeatherByLocationLocalImpl : RepositoryWeatherByLocation {
    override fun getWeather(city: City, callback: MySuperCallbackCity) {
        val listCities = getRussianCities().toMutableList()
        listCities.addAll(getWorldCities())
        val response = listCities.filter { it.city.lat == city.lat && it.city.lon == city.lon }
        callback.onResponse(response.first())
    }
}