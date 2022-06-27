package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentWeatherListRecyclerItemsBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather

class WeatherListAdapter(private val dataList:List<Weather>): RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

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

    class WeatherViewHolder (view: View): RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            val binding = FragmentWeatherListRecyclerItemsBinding.bind(itemView)
            binding.cityName.text = weather.city.name
        }
    }

}