package ru.mingaleev.weatherandroidkotlin

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mingaleev.weatherandroidkotlin.model.room.WeatherDatabase
import ru.mingaleev.weatherandroidkotlin.utils.ROOM_DB_NAME

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        private var weatherDatabase: WeatherDatabase? = null
        private var retrofit: Retrofit.Builder? = null

        fun getMyApp() = myApp!!

        fun getWeatherDatabase(): WeatherDatabase {
            if (weatherDatabase == null) {
                weatherDatabase =
                    Room.databaseBuilder(getMyApp(), WeatherDatabase::class.java, ROOM_DB_NAME)
                        .build()
            }
            return weatherDatabase!!
        }

        fun getRetrofitYandex(): Retrofit.Builder? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://api.weather.yandex.ru")
                    .addConverterFactory(
                        GsonConverterFactory.create(GsonBuilder().setLenient().create())
                    )
            }
            return retrofit
        }
    }
}