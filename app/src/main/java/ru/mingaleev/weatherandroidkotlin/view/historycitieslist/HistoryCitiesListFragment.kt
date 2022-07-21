package ru.mingaleev.weatherandroidkotlin.view.historycitieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentHistoryCitiesListBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.view.details.DetailsFragment
import ru.mingaleev.weatherandroidkotlin.view.details.OnItemClick
import ru.mingaleev.weatherandroidkotlin.viewmodel.historylistcities.AppStateHistoryCitiesList
import ru.mingaleev.weatherandroidkotlin.viewmodel.historylistcities.HistoryCitiesListViewModel

class HistoryCitiesListFragment : Fragment(), OnItemClick {

    private lateinit var binding: FragmentHistoryCitiesListBinding
    private lateinit var viewModel: HistoryCitiesListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryCitiesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryCitiesListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getHistoryCitiesList()
    }

    private fun renderData(appState: AppStateHistoryCitiesList){
        when (appState) {
            is AppStateHistoryCitiesList.Error -> {

                }
            AppStateHistoryCitiesList.Loading -> {
                binding.setStateFragment(appState)
            }
            is AppStateHistoryCitiesList.SuccessListCity -> {
                binding.historyFragmentRecyclerView.adapter =
                    HistoryCitiesListAdapter(appState.weatherListData, this)
                binding.setStateFragment(appState)
            }
        }
    }

    private fun FragmentHistoryCitiesListBinding.setStateFragment(appState: AppStateHistoryCitiesList) {
        when (appState) {
            is AppStateHistoryCitiesList.Loading -> {
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