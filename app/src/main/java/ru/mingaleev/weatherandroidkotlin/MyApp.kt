package ru.mingaleev.weatherandroidkotlin

import android.app.Application
import androidx.room.Room
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
        fun getMyApp() = myApp!!
        fun getWeatherDatabase(): WeatherDatabase {
            if (weatherDatabase == null) {
                weatherDatabase =
                    Room.databaseBuilder(getMyApp(), WeatherDatabase::class.java, ROOM_DB_NAME)
                        .build()
            }
            return weatherDatabase!!
        }
    }
}