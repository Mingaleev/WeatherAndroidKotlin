package ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.Location
import ru.mingaleev.weatherandroidkotlin.model.RepositoryCitiesListImpl
import ru.mingaleev.weatherandroidkotlin.model.RepositoryCitiesList
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList.*
import kotlin.random.Random

class CitiesListViewModel(
    private val liveData: MutableLiveData<AppStateWeatherList> = MutableLiveData<AppStateWeatherList>()
) : ViewModel() {

    private lateinit var repositoryCitiesList: RepositoryCitiesList
    fun getLiveData(): MutableLiveData<AppStateWeatherList> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repositoryCitiesList = RepositoryCitiesListImpl()
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
            Thread.sleep(100L)
            val rand = Random(System.nanoTime())
            if ((1..3).random(rand) != 1) {
                liveData.postValue(SuccessListCity(repositoryCitiesList.getListWeather(location)))
            } else {
                liveData.postValue(
                    Error(IllegalStateException("Ошибка загрузки"))
                )
            }
        }.start()
    }
}