package ru.mingaleev.weatherandroidkotlin.model.dto


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: Fact,
    val info: Info,
) : Parcelable