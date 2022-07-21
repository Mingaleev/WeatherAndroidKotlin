package ru.mingaleev.weatherandroidkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mingaleev.weatherandroidkotlin.domain.City

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val city: City,
    val temperature: Int,
    val feelsLike: Int
)