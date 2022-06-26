package ru.mingaleev.weatherandroidkotlin.domain

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 25,
    val feelsLike: Int = 28
)

data class City(
    val name: String,
    val lat: Double,
    val lon: Double
)

fun getDefaultCity() = City("Казань", 55.8304307, 49.06608060000008)