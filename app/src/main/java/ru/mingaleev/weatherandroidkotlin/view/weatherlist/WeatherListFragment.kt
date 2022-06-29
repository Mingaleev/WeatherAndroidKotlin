package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentWeatherListBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.view.details.DetailsFragment
import ru.mingaleev.weatherandroidkotlin.view.details.OnItemClick
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState

class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var fabRForWorld = false

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
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })

        binding.FragmentFAB.setOnClickListener() {
            if (fabRForWorld) {
                viewModel.getWeatherListForRussia()
                binding.FragmentFAB.setImageResource(R.drawable.ic_russia)
                fabRForWorld = false
            } else {
                viewModel.getWeatherListForWorld()
                binding.FragmentFAB.setImageResource(R.drawable.ic_earth)
                fabRForWorld = true
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                Toast.makeText(requireContext(), appState.error.toString(), Toast.LENGTH_SHORT).show()
            }
            AppState.Loading -> {}
            is AppState.SuccessListCity -> {
                binding.mainFragmentRecyclerView.adapter = WeatherListAdapter(appState.weatherListData, this)
            }
            is AppState.SuccessCity -> TODO()
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}