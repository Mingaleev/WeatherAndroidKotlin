package ru.mingaleev.weatherandroidkotlin.model.dto


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Info(
    val lat: Double,
    val lon: Double,
) : Parcelable