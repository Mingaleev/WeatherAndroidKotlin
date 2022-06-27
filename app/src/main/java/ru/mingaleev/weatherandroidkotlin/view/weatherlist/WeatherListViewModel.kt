package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.*
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState.*
import kotlin.random.Random

class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    lateinit var repositoryListCity: RepositoryListCity
//    Раскоментировать когда добавим погоду с яндекса
//    lateinit var repositoryCity: RepositoryCity
    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
//        Раскоментировать когда добавим погоду с яндекса
//        repositoryCity = if (isConnection()) {
//            RepositoryIRemoteImpl()
//        } else {
//            RepositoryILocalImpl()
//        }
        repositoryListCity = RepositoryILocalImpl()
    }

    private fun isConnection(): Boolean {
        return false
    }

    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {
        liveData.value = Loading
        val rand = Random(System.nanoTime())
        if ((1..3).random(rand) != 1) {
            liveData.postValue(SuccessListCity(repositoryListCity.getListWeather(location)))
        } else {
            liveData.postValue(Error(
            error = IllegalStateException("Ошибка загрузки")
            ))
        }

    }
}