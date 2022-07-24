package ru.mingaleev.weatherandroidkotlin.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okio.IOException
import ru.mingaleev.weatherandroidkotlin.domain.City
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.*
import ru.mingaleev.weatherandroidkotlin.model.retrofit.RepositoryWeatherByLocationRetrofitImpl

class DetailsViewModel(
    private val liveData: MutableLiveData<AppStateDetails> = MutableLiveData<AppStateDetails>()
) : ViewModel() {

    private lateinit var repositoryWeatherByLocation: RepositoryWeatherByLocation
    private lateinit var repositoryAddWeatherToDB: RepositoryAddWeatherToDB

    fun getLiveData(): MutableLiveData<AppStateDetails> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        if (isConnection()){
            repositoryWeatherByLocation = when (2) {
                1 -> {
                    RepositoryWeatherByLocationOkHttpImpl()
                }
                2 -> {
                    RepositoryWeatherByLocationRetrofitImpl()
                }
                3 -> {
                    RepositoryWeatherByLocationWeatherLoaderImpl()
                }
                4 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryWeatherByLocationLocalImpl()
                }
            }
        } else {
            repositoryWeatherByLocation = RepositoryRoomImpl()
        }


        repositoryAddWeatherToDB = when(0) {
            0 -> {
                RepositoryRoomImpl()
            }
            else -> {
                RepositoryRoomImpl()
            }
        }
    }

    fun getWeather(city: City) {
        choiceRepository()
        liveData.value = AppStateDetails.Loading
        repositoryWeatherByLocation.getWeather(city, callback)
    }

    private val callback = object : MySuperCallbackCity {
        override fun onResponse(weather: Weather) {
            repositoryAddWeatherToDB.addWeather(weather)
            liveData.postValue(AppStateDetails.Success(weather))
        }

        override fun onFailure(e: Throwable) {
            liveData.postValue(AppStateDetails.Error(e))
        }
    }

    private fun isConnection (): Boolean{
        return true
    }
}