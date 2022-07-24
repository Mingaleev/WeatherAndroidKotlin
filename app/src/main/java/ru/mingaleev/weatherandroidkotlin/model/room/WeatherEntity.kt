package ru.mingaleev.weatherandroidkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 1,
    var nameCity: String = "",
    var lat: Double = 1.0,
    var lon: Double = 1.0,
    var temperature: Int = 1,
    var feelsLike: Int = 1
)