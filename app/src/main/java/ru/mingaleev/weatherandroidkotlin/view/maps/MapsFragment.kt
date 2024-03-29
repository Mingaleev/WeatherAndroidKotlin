package ru.mingaleev.weatherandroidkotlin.view.maps

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentMapsUiBinding
import ru.mingaleev.weatherandroidkotlin.utils.REQUEST_CODE_LOCATION
import java.util.*


class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.uiSettings.isZoomControlsEnabled = true
        checkPermission(googleMap)
        map = googleMap
    }

    private var _binding: FragmentMapsUiBinding? = null
    private val binding: FragmentMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsUiBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchAddress.text
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        if (Locale.getDefault().language.toString() == "ru"){
            binding.searchAddress.setText("Казань")
        }
        binding.buttonSearch.setOnClickListener{
            searchLocationByAddress(binding.searchAddress.text.toString())
        }
    }

    private fun searchLocationByAddress (address : String) {
        address.let {
            val geocoder = Geocoder(context)
            val result = geocoder.getFromLocationName(it, 1)
            if (result.size > 0) {
                val latLng = LatLng(result.first().latitude, result.first().longitude)
                map.clear()
                map.addMarker(MarkerOptions().position(latLng).title(it))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                binding.mapsSearchError.isVisible = false
            } else {
                Toast.makeText(context, "Такого города не найдено", Toast.LENGTH_LONG).show()
                binding.mapsSearchError.isVisible = true
                binding.mapsSearchError.text = "Город не найдет, попробуйте снова"
            }
        }
    }

    private fun setButtonMyLocation (googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            binding.mapsSearchError.isVisible = false
        } else {
            checkPermission(googleMap)
        }
    }

    private fun checkPermission(googleMap: GoogleMap) {
        map = googleMap
        val permFineLocResult = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (permFineLocResult) {
            setButtonMyLocation(googleMap)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("Для отображения вашего местоположения необходимо Разрешение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest()
                }.setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest()
            showNoStoragePermissionSnackbar()
        }
    }

    private fun showNoStoragePermissionSnackbar() {
        Snackbar.make(binding.root, "Разрешите доступ Настройки -> Права -> Геоданные", 5000)
            .setAction("НАСТРОЙКИ") {
                openApplicationSettings()
            }
            .show()
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:ru.mingaleev.weatherandroidkotlin")
        )
        startActivityForResult(appSettingsIntent, REQUEST_CODE_LOCATION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            setButtonMyLocation(map)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun permissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ), REQUEST_CODE_LOCATION
            )
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
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
                    setButtonMyLocation(map)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}