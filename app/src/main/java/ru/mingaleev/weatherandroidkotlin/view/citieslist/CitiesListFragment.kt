package ru.mingaleev.weatherandroidkotlin.view.citieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentCitiesListBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.utils.SP_DB_NAME
import ru.mingaleev.weatherandroidkotlin.utils.SP_KEY_DB
import ru.mingaleev.weatherandroidkotlin.utils.createAndShowSnackbar
import ru.mingaleev.weatherandroidkotlin.view.details.DetailsFragment
import ru.mingaleev.weatherandroidkotlin.view.details.OnItemClick
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.AppStateWeatherList
import ru.mingaleev.weatherandroidkotlin.viewmodel.citieslist.CitiesListViewModel

class CitiesListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private var fabRForWorld = false

    private lateinit var binding: FragmentCitiesListBinding
    private lateinit var viewModel: CitiesListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCitiesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        val sp = requireActivity().getSharedPreferences(SP_DB_NAME, Context.MODE_PRIVATE)
        fabRForWorld = sp.getBoolean(SP_KEY_DB, false)
        setRussiaOrWorld()

        binding.FragmentFAB.setOnClickListener() {
            setRussiaOrWorld()
            sp.edit().apply{
                putBoolean(SP_KEY_DB, !fabRForWorld)
                apply()
            }
        }
    }

    private fun setRussiaOrWorld (){
        fabRForWorld = if (fabRForWorld) {
            viewModel.getWeatherListForWorld()
            binding.FragmentFAB.setImageResource(R.drawable.ic_earth)
            false
        } else {
            viewModel.getWeatherListForRussia()
            binding.FragmentFAB.setImageResource(R.drawable.ic_russia)
            true
        }
    }

    private fun renderData(appState: AppStateWeatherList){
        when (appState) {
            is AppStateWeatherList.Error -> {
                binding.mainFragmentRecyclerView.createAndShowSnackbar( "Ошибка загрузки", "Еще раз", Snackbar.LENGTH_LONG)
                    {
                        if (!fabRForWorld) {
                            viewModel.getWeatherListForRussia()
                        } else {
                            viewModel.getWeatherListForWorld()
                        }
                    }
                }
            AppStateWeatherList.Loading -> {
                binding.setStateFragment(appState)
            }
            is AppStateWeatherList.SuccessListCity -> {
                binding.mainFragmentRecyclerView.adapter =
                    CitiesListAdapter(appState.weatherListData, this)
                binding.setStateFragment(appState)
            }
        }
    }

    private fun FragmentCitiesListBinding.setStateFragment(appState: AppStateWeatherList) {
        when (appState) {
            is AppStateWeatherList.Loading -> {
                this.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            else -> {
                this.mainFragmentLoadingLayout.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}