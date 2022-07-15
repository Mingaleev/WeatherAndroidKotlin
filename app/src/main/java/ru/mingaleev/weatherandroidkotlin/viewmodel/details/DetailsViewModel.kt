package ru.mingaleev.weatherandroidkotlin.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.*

class DetailsViewModel(
    private val liveData: MutableLiveData<AppStateDetails> = MutableLiveData<AppStateDetails>()
) : ViewModel() {

    private lateinit var repository: RepositoryDetails
    fun getLiveData(): MutableLiveData<AppStateDetails> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = when (1) {
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

    fun getWeather (lat: Double, lon: Double) {
        choiceRepository()
        liveData.value = AppStateDetails.Loading
//        liveData.postValue(AppStateDetails.Success(repository.getWeather(lat, lon)))
        liveData.value = AppStateDetails.Error(IllegalStateException("Ошибка загрузки"))


    }
}