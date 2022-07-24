package ru.mingaleev.weatherandroidkotlin.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import ru.mingaleev.weatherandroidkotlin.BuildConfig
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.API_KEY_YANDEX
import ru.mingaleev.weatherandroidkotlin.utils.converterWeatherDtoToWeather
import ru.mingaleev.weatherandroidkotlin.utils.getLines
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.UncheckedIOException
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryWeatherByLocationWeatherLoaderImpl: RepositoryWeatherByLocation {
    override fun getWeather(city: City, callback: MySuperCallbackCity) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        Thread {
            var myConnection: HttpsURLConnection? = null
            try {
                myConnection = uri.openConnection() as HttpsURLConnection
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(API_KEY_YANDEX, BuildConfig.WEATHER_API_KEY)
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(converterWeatherDtoToWeather(weatherDTO, city.name))
            } catch (e: JSONException) {
                callback.onFailure(e)
            } catch (e: MalformedURLException) {
                callback.onFailure(e)
            } catch (e: IllegalStateException) {
                callback.onFailure(e)
            } catch (e: JsonSyntaxException) {
                callback.onFailure(e)
            }  catch (e: UncheckedIOException) {
                callback.onFailure(e)
            } finally {
                myConnection?.disconnect()
            }
        }.start()
    }
}