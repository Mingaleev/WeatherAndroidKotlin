package ru.mingaleev.weatherandroidkotlin.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import ru.mingaleev.weatherandroidkotlin.R
import ru.mingaleev.weatherandroidkotlin.utils.CHANNEL_HIGH_ID
import ru.mingaleev.weatherandroidkotlin.utils.CHANNEL_HIGH_NAME
import ru.mingaleev.weatherandroidkotlin.utils.NOTIFICATION_ID

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("@@@", token)
        pushNotification(token, token)
        super.onNewToken(token)
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
}