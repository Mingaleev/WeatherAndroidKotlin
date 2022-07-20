package ru.mingaleev.weatherandroidkotlin.model.retrofit

import com.google.gson.GsonBuilder
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.MyLargeSuperCallback
import ru.mingaleev.weatherandroidkotlin.model.RepositoryDetails
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO

class RepositoryDetailsRetrofitImpl : RepositoryDetails {
    override fun getWeather(city: City, callback: MyLargeSuperCallback) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://api.weather.yandex.ru")
        retrofitImpl.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        val api = retrofitImpl.build().create(WeatherAPI::class.java)

        api.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    if (response.isSuccessful && response.body() != null) {
                        callback.onResponse(response.body()!!)
                    } else {
                        callback.onFailure(IOException("403/404 Error"))
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    callback.onFailure(t as IOException /* = java.io.IOException */) //костыль
                }

            })

    }
}