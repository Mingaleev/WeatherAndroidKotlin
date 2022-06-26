package ru.mingaleev.weatherandroidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mingaleev.weatherandroidkotlin.databinding.ActivityMainBinding
import ru.mingaleev.weatherandroidkotlin.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}