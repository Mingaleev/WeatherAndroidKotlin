package ru.mingaleev.weatherandroidkotlin.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: Fact,
    @SerializedName("info")
    val info: Info,
    @SerializedName("now")
    val now: Int,
) : Parcelable