package com.emmanuel.pastor.simplesmartapps.tricycle.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.emmanuel.pastor.simplesmartapps.measurements.Measure.Temperature
import com.emmanuel.pastor.simplesmartapps.measurements.TemperatureUnit
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.TricycleRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class TricycleService : LifecycleService() {
    companion object {
        private const val REFRESH_INTERVAL_MILLIS = 10_000L
        private const val SERVICE_NOTIFICATION_ID = 10000
        private const val MOTOR_OVERHEAT_NOTIFICATION_ID = 10001
        private const val BATTERY_OVERHEAT_NOTIFICATION_ID = 10002
        private val BATTERY_TEMP_THRESHOLD_CELSIUS = Temperature(45, TemperatureUnit.Celsius)
        private val MOTOR_TEMP_THRESHOLD_CELSIUS = Temperature(150, TemperatureUnit.Celsius)
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

        createNotificationChannels()

        lifecycleScope.launch {
            monitorOverheating()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createServiceNotification()
        startForeground(SERVICE_NOTIFICATION_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannels() {
        createServiceNotificationChannel()
        createWarningNotificationChannel()
    }

    private fun createServiceNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notif_channel_id_tricycle_service),
            getString(R.string.notif_channel_name_services),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notif_channel_description_tricycle_service)
            enableVibration(false)
            enableLights(false)
            setShowBadge(false)
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createWarningNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.notif_channel_id_warning),
            getString(R.string.notif_channel_name_warnings),
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.notif_channel_description_warnings)
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createServiceNotification() = Notification.Builder(this, getString(R.string.notif_channel_id_tricycle_service))
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.notif_content_tricycle_service))
        .setSmallIcon(R.drawable.ic_notif_service)
        .setOnlyAlertOnce(true)
        .setCategory(Notification.CATEGORY_SERVICE)
        .build()

    private suspend inline fun monitorOverheating() =
        tricycleRepo.fetchTricycleData().collect { tricycleData ->
            if (tricycleData.lastUpdated == null || (System.currentTimeMillis() / 1000).seconds - tricycleData.lastUpdated.seconds > REFRESH_INTERVAL_MILLIS.seconds * 2) {
                return@collect
            }

            tricycleData.batteryTemperature?.let { batteryTemp ->
                if (batteryTemp > BATTERY_TEMP_THRESHOLD_CELSIUS) {
                    createWarningNotification(
                        "Battery overheating",
                        "STOP USING YOUR TRICYCLE! The battery temperature is above the safety threshold.",
                        R.drawable.ic_notif_battery_overheat
                    ).also { notificationManager.notify(BATTERY_OVERHEAT_NOTIFICATION_ID, it) }
                }
            }

            tricycleData.motorTemperature?.let { motorTemp ->
                if (motorTemp > MOTOR_TEMP_THRESHOLD_CELSIUS) {
                    createWarningNotification(
                        "Motor overheating",
                        "STOP USING YOUR TRICYCLE! The motor temperature is above the safety threshold.",
                        R.drawable.ic_notif_motor_overheat
                    ).also { notificationManager.notify(MOTOR_OVERHEAT_NOTIFICATION_ID, it) }
                }
            }
        }

    private fun createWarningNotification(title: String, content: String, @DrawableRes icon: Int) =
        Notification.Builder(this, getString(R.string.notif_channel_id_warning))
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon)
            .setOnlyAlertOnce(true)
            .setCategory(Notification.CATEGORY_ALARM)
            .build()
}