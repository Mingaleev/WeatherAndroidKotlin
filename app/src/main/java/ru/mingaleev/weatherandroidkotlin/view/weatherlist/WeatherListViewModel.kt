package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState
import java.lang.Thread.sleep

class WeatherListViewModel(val liveData:MutableLiveData<AppState> = MutableLiveData<AppState>()): ViewModel() {

    fun sentRequest() {
        liveData.value =AppState.Loading
        Thread{
            sleep(2000L)
            liveData.value =AppState.Success(Any())
        }
    }
}