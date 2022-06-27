package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.domain.getRussianCities
import ru.mingaleev.weatherandroidkotlin.domain.getWorldCities

class RepositoryILocalImpl : RepositoryListCity, RepositoryCity {
    override fun getListWeather(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> { getRussianCities() }
            Location.World -> { getWorldCities() }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}