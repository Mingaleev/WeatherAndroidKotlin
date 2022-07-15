package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.domain.getDefaultCity

class RepositoryDetailsLocalImpl: RepositoryDetails {
    override fun getCitiesList(lat: Double, lon: Double): Weather {
        return Weather(getDefaultCity())
    }
}