package ru.mingaleev.weatherandroidkotlin.model

import com.google.gson.Gson
import okio.IOException
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.API_KEY_YANDEX
import ru.mingaleev.weatherandroidkotlin.utils.converterWeatherDtoToWeather
import ru.mingaleev.weatherandroidkotlin.utils.getLines
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailsWeatherLoaderImpl: RepositoryDetails {
    override fun getWeather(city: City, callback: MyLargeSuperCallback) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        Thread {
            var myConnection: HttpsURLConnection? = null
            try {
                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(converterWeatherDtoToWeather(weatherDTO, city.name))
            } catch (e: IOException) {
                callback.onFailure(e)
                myConnection?.disconnect()
            } finally {
                myConnection?.disconnect()
            }
        }.start()
    }
}