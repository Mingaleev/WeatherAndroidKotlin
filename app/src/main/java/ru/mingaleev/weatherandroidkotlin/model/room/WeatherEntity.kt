package ru.mingaleev.weatherandroidkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 1,
    val nameCity: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val temperature: Int = 0,
    val feelsLike: Int = 0
)