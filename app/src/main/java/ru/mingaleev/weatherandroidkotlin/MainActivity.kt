package ru.mingaleev.weatherandroidkotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import ru.mingaleev.weatherandroidkotlin.databinding.ActivityMainBinding
import ru.mingaleev.weatherandroidkotlin.utils.CHANNEL_HIGH_ID
import ru.mingaleev.weatherandroidkotlin.utils.CHANNEL_HIGH_NAME
import ru.mingaleev.weatherandroidkotlin.utils.NOTIFICATION_ID
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

        pushNotification("ПОГОДА СЕЙЧАС", "Узнайте погоду сейчас, что бы быть готовым ко всему")
    }


    private fun pushNotification(title: String, body: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(this, CHANNEL_HIGH_ID).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(R.drawable.maps)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelHigh = NotificationChannel(
                CHANNEL_HIGH_ID,
                CHANNEL_HIGH_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channelHigh.description = "Канал для важных уведомлений"
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
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