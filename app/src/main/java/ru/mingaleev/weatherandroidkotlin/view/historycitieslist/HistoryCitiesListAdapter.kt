package ru.mingaleev.weatherandroidkotlin.view.historycitieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentWeatherListRecyclerItemsBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.view.details.OnItemClick

class HistoryCitiesListAdapter(private val dataList:List<Weather>, private val callback: OnItemClick): RecyclerView.Adapter<HistoryCitiesListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = FragmentWeatherListRecyclerItemsBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class WeatherViewHolder (view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            FragmentWeatherListRecyclerItemsBinding.bind(itemView).also {
                it.cityName.text = weather.city.name
                it.root.setOnClickListener{
                    callback.onItemClick(weather)
                }
            }

        }
    }

}