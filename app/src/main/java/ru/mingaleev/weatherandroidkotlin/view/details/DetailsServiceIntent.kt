package ru.mingaleev.weatherandroidkotlin.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsServiceIntent: IntentService("") {
    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?. let { city ->
                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
                Thread {
                    var myConnection: HttpsURLConnection? = null
                    try {
                        myConnection = uri.openConnection() as HttpsURLConnection
                        myConnection.readTimeout = 5000
                        myConnection.addRequestProperty(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
                        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                        val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                            putExtra(BUNDLE_WEATHER_DTO_KEY, weatherDTO)
                            action = WAVE_WEATHER_DTO
                        })
                    } catch (e: Exception) {
                        myConnection?.disconnect()
                    } finally {
                        myConnection?.disconnect()
                    }
                }.start()
            }
        }
    }

}