package ru.mingaleev.weatherandroidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mingaleev.weatherandroidkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}