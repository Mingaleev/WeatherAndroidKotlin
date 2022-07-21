package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.MyApp
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.room.WeatherEntity

class RepositoryHistoryCitiesListImp: RepositoryHistoryCitiesList {
    override fun getHistoryListWeather(callbackListCities: MySuperCallbackListCities) {
        Thread{
            callbackListCities.onResponse(convertorListWeatherEntityToWeather(
                MyApp.getWeatherDatabase().weatherDAO().getWeatherAll()))
        }.start()
    }

    private fun convertorListWeatherEntityToWeather(weatherEntity: List<WeatherEntity>): List<Weather>{
        return weatherEntity.map {
            Weather(
                City(it.nameCity,it.lat, it.lon),
                it.temperature,
                it.feelsLike
            )
        }
    }
}