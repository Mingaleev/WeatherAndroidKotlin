package ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.Location
import ru.mingaleev.weatherandroidkotlin.model.RepositoryCitiesList
import ru.mingaleev.weatherandroidkotlin.model.RepositoryCitiesListImpl
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList.Loading
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList.SuccessListCity

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
                liveData.postValue(SuccessListCity(repositoryCitiesList.getListWeather(location)))
        }.start()
    }
}