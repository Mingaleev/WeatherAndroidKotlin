package ru.mingaleev.weatherandroidkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.mingaleev.weatherandroidkotlin.databinding.ActivityMainBinding
import ru.mingaleev.weatherandroidkotlin.view.citieslist.CitiesListFragment
import ru.mingaleev.weatherandroidkotlin.view.contentprovaider.ContentProviderFragment
import ru.mingaleev.weatherandroidkotlin.view.historycitieslist.HistoryCitiesListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, HistoryCitiesListFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_content_provider -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, ContentProviderFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}