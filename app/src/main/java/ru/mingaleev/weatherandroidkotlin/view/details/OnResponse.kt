package ru.mingaleev.weatherandroidkotlin.view.details

import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO

fun interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}