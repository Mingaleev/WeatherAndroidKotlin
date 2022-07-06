package ru.mingaleev.weatherandroidkotlin.model.dto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val fact: Fact,
    @SerializedName("info")
    val info: Info,
    @SerializedName("now")
    val now: Int,
)