package ru.mingaleev.weatherandroidkotlin.model.room

import android.database.Cursor
import androidx.room.*

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(weatherEntity: WeatherEntity)

    @Update
    fun updateRoom(weatherEntity:WeatherEntity)

    @Query("SELECT * FROM WeatherEntity WHERE lat=:inLat AND lon=:inLon")
    fun getWeatherByLocation(inLat: Double, inLon: Double): List<WeatherEntity>

    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherAll(): List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, nameCity, temperature FROM WeatherEntity")
    fun getWeatherCursor(): Cursor

    @Query("SELECT id, nameCity, temperature FROM WeatherEntity WHERE id = :id ")
    fun getWeatherCursor(id: Long): Cursor
}