package ru.mingaleev.weatherandroidkotlin.model

import ru.mingaleev.weatherandroidkotlin.MyApp
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.room.WeatherEntity

class RepositoryRoomImpl: RepositoryWeatherByLocation, RepositoryAddWeatherToDB {

    override fun getWeather(city: City, callback: MySuperCallbackCity) {
        Thread{
            callback.onResponse(
                convertorWeatherEntityToWeather(
                    MyApp.getWeatherDatabase().weatherDAO().getWeatherByLocation(city.lat, city.lon).first()))
        }.start()
    }

    override fun addWeather(weather: Weather) {
        Thread{
            MyApp.getWeatherDatabase().weatherDAO().insert(convertorWeatherToWeatherEntity(weather))
        }.start()
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





