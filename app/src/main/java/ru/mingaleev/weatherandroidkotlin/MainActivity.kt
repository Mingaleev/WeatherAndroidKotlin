package ru.mingaleev.weatherandroidkotlin

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.mingaleev.weatherandroidkotlin.databinding.ActivityMainBinding
import ru.mingaleev.weatherandroidkotlin.view.citieslist.CitiesListFragment
import ru.mingaleev.weatherandroidkotlin.view.contentprovaider.ContentProviderFragment
import ru.mingaleev.weatherandroidkotlin.view.historycitieslist.HistoryCitiesListFragment
import ru.mingaleev.weatherandroidkotlin.view.maps.MapsFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }

        //  Для проверки ДЗ
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            Log.d("@@@", task.result)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history -> {
                transaction(HistoryCitiesListFragment(), "tagHistory")
                return true
            }
            R.id.menu_content_provider -> {
                transaction(ContentProviderFragment(), "tagCP")
                return true
            }
            R.id.menu_maps -> {
                transaction(MapsFragment(), "tagMaps")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun transaction(fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss()
            }
        } else {
            supportFragmentManager.findFragmentByTag(tag)?.let {
                supportFragmentManager.popBackStack(tag, 1)
            }
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.container, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss()
            }
        }
    }
}