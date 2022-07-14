package ru.mingaleev.weatherandroidkotlin.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentDetailsWeatherBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.model.dto.WeatherDTO
import ru.mingaleev.weatherandroidkotlin.utils.BUNDLE_CITY_KEY
import ru.mingaleev.weatherandroidkotlin.utils.BUNDLE_WEATHER_DTO_KEY
import ru.mingaleev.weatherandroidkotlin.utils.BUNDLE_WEATHER_EXTRA
import ru.mingaleev.weatherandroidkotlin.utils.WAVE_WEATHER_DTO

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(weather: Weather): DetailsFragment {
            val fr = DetailsFragment()
            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
            return fr
        }
    }

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val binding: FragmentDetailsWeatherBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)

        weather?.let { weatherLocal ->

            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        intent?.let {
                            it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)?.let {
                                renderData(weatherLocal.apply {
                                    temperature = it.fact.temp
                                    feelsLike = it.fact.feelsLike
                                })
                            }
                        }
                    }
                }, IntentFilter(WAVE_WEATHER_DTO)
            )

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsServiceIntent::class.java
                ).apply {
                    putExtra(BUNDLE_CITY_KEY, weatherLocal.city)
                })
        }
    }

    private fun renderData(weather: Weather) {
        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat} / ${weather.city.lon}"
        }

    }
}