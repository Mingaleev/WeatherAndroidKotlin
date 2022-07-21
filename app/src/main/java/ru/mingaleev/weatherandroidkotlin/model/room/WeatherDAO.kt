package ru.mingaleev.weatherandroidkotlin.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mingaleev.weatherandroidkotlin.domain.City

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity WHERE lat=:inLat AND lon=:inLon")
    fun getWeatherByLocation(inLat: Double, inLon: Double): List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherAll(): List<WeatherEntity>
}