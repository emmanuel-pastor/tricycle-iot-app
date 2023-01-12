package com.emmanuel.pastor.simplesmartapps.tricycle.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.TricycleRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TricycleService : LifecycleService() {
    companion object {
        private const val REFRESH_INTERVAL_MILLIS = 10_000L
        private const val SERVICE_NOTIFICATION_ID = 10000
    }

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var tricycleRepo: TricycleRepo

    override fun onCreate() {
        super.onCreate()

        lifecycleScope.launch {
            while (true) {
                tricycleRepo.refreshTricycleData()
                delay(REFRESH_INTERVAL_MILLIS)
            }
        }

        createNotificationChannel()
    }

    @SuppressLint("MissingSuperCall")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(SERVICE_NOTIFICATION_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notif_channel_id_tricycle_service),
            getString(R.string.notif_channel_name_services),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notif_channel_description_tricycle_service)
            enableVibration(false)
            enableLights(false)
            setShowBadge(false)
            setSound(null, null)
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification() = Notification.Builder(this, getString(R.string.notif_channel_id_tricycle_service))
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.notif_content_tricycle_service))
        .setSmallIcon(R.drawable.ic_notif_service)
        .setOnlyAlertOnce(true)
        .setCategory(Notification.CATEGORY_SERVICE)
        .build()
}