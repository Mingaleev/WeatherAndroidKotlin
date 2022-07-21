package ru.mingaleev.weatherandroidkotlin.viewmodel.historylistcities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.*
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList.Loading
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList.SuccessListCity
import ru.mingaleev.weatherandroidkotlin.viewmodel.details.AppStateDetails
import java.io.IOException

class HistoryCitiesListViewModel(
    private val liveData: MutableLiveData<AppStateHistoryCitiesList> = MutableLiveData<AppStateHistoryCitiesList>()
) : ViewModel() {

    private lateinit var repositoryHistoryCitiesList: RepositoryHistoryCitiesList

    fun getLiveData(): MutableLiveData<AppStateHistoryCitiesList> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repositoryHistoryCitiesList = RepositoryHistoryCitiesListImp()
    }


    fun getHistoryCitiesList() {
//        liveData.value = Loading
        repositoryHistoryCitiesList.getHistoryListWeather(callback)

    }

    private val callback = object : MySuperCallbackListCities {
        override fun onResponse(weather: List<Weather>) {
            liveData.postValue(AppStateHistoryCitiesList.SuccessListCity(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(AppStateHistoryCitiesList.Error(e))
        }
    }
}