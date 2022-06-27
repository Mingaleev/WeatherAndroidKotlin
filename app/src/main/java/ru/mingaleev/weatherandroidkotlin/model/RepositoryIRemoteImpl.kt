package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.domain.Weather

class RepositoryIRemoteImpl: RepositoryCity {

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}