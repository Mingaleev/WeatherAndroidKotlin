package ru.mingaleev.weatherandroidkotlin.view.details

import ru.mingaleev.weatherandroidkotlin.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}