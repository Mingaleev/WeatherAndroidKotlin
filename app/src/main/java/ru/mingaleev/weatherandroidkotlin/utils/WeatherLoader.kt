package ru.mingaleev.weatherandroidkotlin.utils

import com.google.gson.Gson
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.view.details.OnResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

object WeatherLoader {

    fun request(lat: Double, lon: Double, onResponse: OnResponse) {

        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.addRequestProperty("X-Yandex-API-Key", "")
        Thread {
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            onResponse.onResponse(weatherDTO)
        }.start()
    }

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}