package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.MyApp
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.room.WeatherEntity
import kotlin.concurrent.thread

class RepositoryRoomImpl: RepositoryWeatherByLocation, RepositoryAddWeatherToDB {
    override fun getWeather(city: City, callback: MyLargeSuperCallback) {
        Thread{
            callback.onResponse(
                convertorWeatherEntityToWeather(
                    MyApp.getWeatherDatabase().weatherDAO().getWeatherByLocation(city.lat, city.lon).first()))
        }.start()
    }

    override fun addWeather(weather: Weather) {
            MyApp.getWeatherDatabase().weatherDAO().insert(convertorWeatherToWeatherEntity(weather))
    }

    private fun convertorWeatherEntityToWeather(weatherEntity: WeatherEntity): Weather{
        return weatherEntity.let {
            Weather(
                City(it.nameCity, it.lat, it.lon),
                it.temperature,
                it.feelsLike
            )
        }
    }

    private fun convertorWeatherToWeatherEntity(weather: Weather): WeatherEntity{
        return WeatherEntity(
            0,
            weather.city.name,
            weather.city.lat,
            weather.city.lon,
            weather.temperature,
            weather.feelsLike
        )
    }
}





