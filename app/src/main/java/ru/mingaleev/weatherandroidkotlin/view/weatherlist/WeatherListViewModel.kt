package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.model.Repository
import ru.mingaleev.weatherandroidkotlin.model.RepositoryILocalImpl
import ru.mingaleev.weatherandroidkotlin.model.RepositoryIRemoteImpl
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState.*

class WeatherListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    lateinit var repository: Repository
    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = if (isConnection()) {
            RepositoryIRemoteImpl()
        } else {
            RepositoryILocalImpl()
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

    fun sentRequest() {
        liveData.value = Loading
        if ((1..3).random() != 1) {
            liveData.postValue(Success(repository.getWeather(55.755826, 37.617299900000035)))
        } else {
            liveData.postValue(Error(throw IllegalStateException("Ошибка загрузки")))
        }

    }
}