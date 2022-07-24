package ru.mingaleev.weatherandroidkotlin.model.retrofit

import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.MyApp.Companion.getRetrofitYandex
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.MySuperCallbackCity
import ru.mingaleev.weatherandroidkotlin.model.RepositoryWeatherByLocation
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.converterWeatherDtoToWeather

class RepositoryWeatherByLocationRetrofitImpl : RepositoryWeatherByLocation {
    override fun getWeather(city: City, callback: MySuperCallbackCity) {
        getRetrofitYandex()?.run {
            build().create(WeatherAPI::class.java)
                .getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
                .enqueue(object : Callback<WeatherDTO> {
                    override fun onResponse(
                        call: Call<WeatherDTO>,
                        response: Response<WeatherDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            callback.onResponse(
                                converterWeatherDtoToWeather(
                                    response.body()!!,
                                    city.name
                                )
                            )
                        } else {
                            callback.onFailure(IOException("403/404 Error"))
                        }
                    }

                    override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                        callback.onFailure(t as IOException /* = java.io.IOException */) //костыль
                    }
                })
        }
    }
}