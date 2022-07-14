package ru.mingaleev.weatherandroidkotlin.utils

import com.google.gson.Gson
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.view.details.OnResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {

    fun request(lat: Double, lon: Double, onResponse: OnResponse) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        Thread {
            var myConnection: HttpsURLConnection? = null
            try {
                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                onResponse.onResponse(weatherDTO)
            } catch (e: Exception) {
                myConnection?.disconnect()
            } finally {
                myConnection?.disconnect()
            }
        }.start()
    }
}