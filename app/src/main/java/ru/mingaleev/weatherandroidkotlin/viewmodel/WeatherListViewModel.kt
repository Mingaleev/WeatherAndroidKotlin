package ru.mingaleev.weatherandroidkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.Location
import ru.mingaleev.weatherandroidkotlin.model.RepositoryILocalImpl
import ru.mingaleev.weatherandroidkotlin.model.RepositoryListCity
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState.*
import kotlin.random.Random

class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    private lateinit var repositoryListCity: RepositoryListCity
//    Раскоментировать когда добавим погоду с яндекса
//    var repositoryCity: RepositoryCity
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

    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {
        liveData.value = Loading
        Thread {
            Thread.sleep(3000L)
            val rand = Random(System.nanoTime())
            if ((1..10).random(rand) != 1) {
                liveData.postValue(SuccessListCity(repositoryListCity.getListWeather(location)))
            } else {
                liveData.postValue(
                    Error(IllegalStateException("Ошибка загрузки"))
                )
            }
        }.start()
    }
}