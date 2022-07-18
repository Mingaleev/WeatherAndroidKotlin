package ru.mingaleev.weatherandroidkotlin.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.API_KEY_YANDEX
import ru.mingaleev.weatherandroidkotlin.utils.RETROFIT_WEATHER_API_LAT
import ru.mingaleev.weatherandroidkotlin.utils.RETROFIT_WEATHER_API_LON

interface WeatherAPI {

    @GET("/v2/informers")
    fun getWeather(
        @Header(API_KEY_YANDEX) keyValue: String,
        @Query(RETROFIT_WEATHER_API_LAT) lat: Double,
        @Query(RETROFIT_WEATHER_API_LON) lon: Double
    ): Call<WeatherDTO>
}