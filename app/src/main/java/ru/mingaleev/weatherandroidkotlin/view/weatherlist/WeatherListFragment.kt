package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentWeatherListBinding
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState

class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private lateinit var binding: FragmentWeatherListBinding
    lateinit var viewModel: WeatherListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner,object : Observer<AppState>{
            override fun onChanged(t: AppState) {
                Toast.makeText(requireContext(), "Работает", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.sentRequest()
    }
}