package ru.mingaleev.weatherandroidkotlin.view.contentprovaider

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
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentContentProviderBinding
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentDetailsWeatherBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.utils.BUNDLE_WEATHER_EXTRA
import ru.mingaleev.weatherandroidkotlin.viewmodel.details.AppStateDetails
import ru.mingaleev.weatherandroidkotlin.viewmodel.details.DetailsViewModel

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding: FragmentContentProviderBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}