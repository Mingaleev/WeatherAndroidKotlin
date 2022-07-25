package ru.mingaleev.weatherandroidkotlin.view.citieslist

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentCitiesListBinding
import ru.mingaleev.weatherandroidkotlin.domain.Weather
import ru.mingaleev.weatherandroidkotlin.utils.REQUEST_CODE_LOCATION
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

        binding.weatherListFragmentFABCities.setOnClickListener() {
            setRussiaOrWorld()
            sp.edit().apply {
                putBoolean(SP_KEY_DB, !fabRForWorld)
                apply()
            }
        }

        binding.weatherListFragmentFABLocation.setOnClickListener() {
            checkPermission()
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000L,
                    0F
                ) { location -> Log.d("@@@", "${location.latitude}, ${location.longitude}") }
            }
        }
    }

    private fun checkPermission() {
        val permResult = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("Для получения погоды по вашему местоположению необходимо Разрешение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionGPSRequest(Manifest.permission.ACCESS_FINE_LOCATION)
                }.setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionGPSRequest(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun permissionGPSRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[i] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setRussiaOrWorld() {
        fabRForWorld = if (fabRForWorld) {
            viewModel.getWeatherListForWorld()
            binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_earth)
            false
        } else {
            viewModel.getWeatherListForRussia()
            binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_russia)
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