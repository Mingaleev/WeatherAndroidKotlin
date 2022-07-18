package ru.mingaleev.weatherandroidkotlin.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okio.IOException
import ru.mingaleev.weatherandroidkotlin.model.*
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO

class DetailsViewModel(
    private val liveData: MutableLiveData<AppStateDetails> = MutableLiveData<AppStateDetails>()
) : ViewModel() {

    private lateinit var repository: RepositoryDetails
    fun getLiveData(): MutableLiveData<AppStateDetails> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = when (8) {
            1 -> {
                RepositoryDetailsOkHttpImpl()
            }
            2 -> {
                RepositoryDetailsRetrofitImpl()
            }
            3 -> {
                RepositoryDetailsWeatherLoaderImpl()
            }
            else -> {
                RepositoryDetailsLocalImpl()
            }
        }
    }

    fun getWeather(lat: Double, lon: Double) {
        choiceRepository()
        liveData.value = AppStateDetails.Loading
        repository.getWeather(lat, lon, callback)
    }

    private val callback = object : MyLargeSuperCallback {
        override fun onResponse(weatherDTO: WeatherDTO) {
            liveData.postValue(AppStateDetails.Success(weatherDTO))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateDetails.Error(e))
        }
    }
}