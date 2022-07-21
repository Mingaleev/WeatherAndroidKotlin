package ru.mingaleev.weatherandroidkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nameCity: String,
    val lat: Double,
    val lon: Double,
    val temperature: Int,
    val feelsLike: Int
)