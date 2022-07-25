package ru.mingaleev.weatherandroidkotlin.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

class Utils {
}

fun View.createAndShowSnackbar (text: String, actionText: String, duration: Int, action: (v: View) -> Unit) {
    Snackbar.make(this, text, duration).setAction(actionText, action).show() }

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun converterWeatherDtoToWeather (weatherDTO: WeatherDTO, nameCity: String): Weather{
    return Weather(
        City(nameCity, weatherDTO.info.lat, weatherDTO.info.lon),
        weatherDTO.fact.temp,
        weatherDTO.fact.feelsLike,
        weatherDTO.fact.icon
    )
}