package ru.mingaleev.weatherandroidkotlin.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.databinding.FragmentMapsUiBinding

class MapsFragment : Fragment() {

    lateinit var map: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.uiSettings.isZoomControlsEnabled = true
        setButtonMyLocation (googleMap)
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

        binding.buttonSearch.setOnClickListener{
            binding.searchAddress.text.toString().let { address ->
                val geocoder = Geocoder(context)
                val result = geocoder.getFromLocationName(address, 1)
                if (result.size > 0) {
                    val latLng = LatLng(result.first().latitude, result.first().longitude)
                    map.clear()
                    map.addMarker(MarkerOptions().position(latLng))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    binding.mapsSearchError.isVisible = false
                } else {
                    Toast.makeText(context, "Такого города не найдено", Toast.LENGTH_LONG).show()
                    binding.mapsSearchError.isVisible = true
                    binding.mapsSearchError.text = "Город не найдет, попробуйте снова"
                }
            }
        }
    }

    private fun setButtonMyLocation (googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}