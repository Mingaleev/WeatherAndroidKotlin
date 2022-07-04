package ru.mingaleev.weatherandroidkotlin.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentWeatherListBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.view.details.DetailsFragment
import ru.mingaleev.weatherandroidkotlin.view.details.OnItemClick
import ru.mingaleev.weatherandroidkotlin.viewmodel.AppState
import ru.mingaleev.weatherandroidkotlin.viewmodel.WeatherListViewModel

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
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        binding.FragmentFAB.setOnClickListener() {
            fabRForWorld = if (fabRForWorld) {
                viewModel.getWeatherListForRussia()
                binding.FragmentFAB.setImageResource(R.drawable.ic_russia)
                false
            } else {
                viewModel.getWeatherListForWorld()
                binding.FragmentFAB.setImageResource(R.drawable.ic_earth)
                true
            }
        }
        viewModel.getWeatherListForRussia()
    }

    private fun renderData(appState: AppState){
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentRecyclerView.createAndShow("Ошибка загрузки", "Еще раз", Snackbar.LENGTH_LONG) { _ ->
                    if (!fabRForWorld) {
                        viewModel.getWeatherListForRussia()
                    } else {
                        viewModel.getWeatherListForWorld()
                    }
                }
            }
            AppState.Loading -> {
                binding.setStateFragment(appState)
            }
            is AppState.SuccessListCity -> {
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherListData, this)
                binding.setStateFragment(appState)
            }
            is AppState.SuccessCity -> TODO()
        }
    }

    private fun FragmentWeatherListBinding.setStateFragment(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                this.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            else -> {
                this.mainFragmentLoadingLayout.visibility = View.GONE
            }
        }
    }

    fun View.createAndShow(text: String, actionText: String, duration: Int, action: (v: View) -> Unit) {
        Snackbar.make(this, text, duration).setAction(actionText, action).show() }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}