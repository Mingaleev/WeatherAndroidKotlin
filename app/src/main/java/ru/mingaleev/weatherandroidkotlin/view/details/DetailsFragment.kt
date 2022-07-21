package ru.mingaleev.weatherandroidkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import ru.mingaleev.weatherandroidkotlin.MyApp
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentDetailsWeatherBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.utils.BUNDLE_WEATHER_EXTRA
import ru.mingaleev.weatherandroidkotlin.viewmodel.details.AppStateDetails
import ru.mingaleev.weatherandroidkotlin.viewmodel.details.DetailsViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val binding: FragmentDetailsWeatherBinding
        get() {
            return _binding!!
        }

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
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
            viewModel.getWeather(weatherLocal.city)
            viewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
    }

    private fun renderData(appStateDetails: AppStateDetails) {
        val imageLoader = ImageLoader.Builder(MyApp.getMyApp()).components{
            add(SvgDecoder.Factory())
        }.build()
        when (appStateDetails) {
            is AppStateDetails.Error -> {}
            AppStateDetails.Loading -> {}
            is AppStateDetails.Success -> {
                with(binding) {
                    cityName.text = appStateDetails.weather.city.name
                    temperatureValue.text = appStateDetails.weather.temperature.toString()
                    feelsLikeValue.text = appStateDetails.weather.feelsLike.toString()
                    cityCoordinates.text =
                        "${appStateDetails.weather.city.lat} / ${appStateDetails.weather.city.lon}"
                    imageWeather.load(
                        "https://yastatic.net/weather/i/icons/funky/dark/${appStateDetails.weather.icon}.svg", imageLoader)
                }
            }
        }
    }

    companion object {
        fun newInstance(weather: Weather): DetailsFragment {
            val fr = DetailsFragment()
            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
            return fr
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}