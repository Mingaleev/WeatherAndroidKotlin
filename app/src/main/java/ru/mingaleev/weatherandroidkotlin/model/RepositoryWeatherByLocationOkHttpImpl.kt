package ru.mingaleev.weatherandroidkotlin.model

import com.google.gson.Gson
import okhttp3.*
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.API_KEY_YANDEX
import ru.mingaleev.weatherandroidkotlin.utils.converterWeatherDtoToWeather
import java.io.IOException

class RepositoryWeatherByLocationOkHttpImpl: RepositoryWeatherByLocation {
    override fun getWeather(city: City, callback: MyLargeSuperCallback) {

        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code in 200..299 && response.body != null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO = Gson().fromJson(responseString, WeatherDTO::class.java)
                        callback.onResponse(converterWeatherDtoToWeather(weatherDTO, city.name))
                    }
                } else {
//                    callback.onFailure()
                }
            }
        })
    }
}





