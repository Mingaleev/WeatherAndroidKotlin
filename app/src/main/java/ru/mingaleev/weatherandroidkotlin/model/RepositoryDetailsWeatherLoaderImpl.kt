package ru.mingaleev.weatherandroidkotlin.model

import com.google.gson.Gson
import okio.IOException
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.API_KEY_YANDEX
import ru.mingaleev.weatherandroidkotlin.utils.getLines
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailsWeatherLoaderImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        Thread {
            var myConnection: HttpsURLConnection? = null
            try {
                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(weatherDTO)
            } catch (e: IOException) {
                callback.onFailure(e)
                myConnection?.disconnect()
            } finally {
                myConnection?.disconnect()
            }
        }.start()
    }
}