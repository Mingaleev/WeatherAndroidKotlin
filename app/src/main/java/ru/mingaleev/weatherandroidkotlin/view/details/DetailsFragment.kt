package ru.mingaleev.weatherandroidkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentDetailsWeatherBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.utils.WeatherLoader

class DetailsFragment : Fragment() {

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "details"
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

        weather?.let {
            WeatherLoader.request(it.city.lat, it.city.lon) {weatherDTO ->
                requireActivity().runOnUiThread {
                    renderData(it.apply {
                        temperature = weatherDTO.fact.temp
                        feelsLike = weatherDTO.fact.feelsLike
                    })
                }
            }
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