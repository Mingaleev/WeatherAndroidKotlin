package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.getRussianCities
import ru.mingaleev.weatherandroidkotlin.domain.getWorldCities

class RepositoryWeatherByLocationLocalImpl : RepositoryWeatherByLocation {
    override fun getWeather(city: City, callback: MyLargeSuperCallback) {
        val listCities = getRussianCities().toMutableList()
        listCities.addAll(getWorldCities())
        val response = listCities.filter { it.city.lat == city.lat && it.city.lon == city.lon }
        callback.onResponse(response.first())
    }

//    private fun convertModelToDTO(weather: Weather): WeatherDTO {
//        return WeatherDTO(
//            Fact(weather.feelsLike, weather.temperature),
//            Info(weather.city.lat, weather.city.lon)
//        )
//    }
}