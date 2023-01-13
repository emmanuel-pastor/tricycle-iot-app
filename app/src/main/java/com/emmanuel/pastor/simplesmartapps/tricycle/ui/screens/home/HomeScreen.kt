package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home

import android.icu.text.RelativeDateTimeFormatter
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emmanuel.pastor.simplesmartapps.measurements.TemperatureUnit
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.components.BatteryCard
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.components.SectionLabel
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.components.SensorCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val sensorSectionState: SensorSectionState by viewModel.sensorSectionState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (sensorSectionState) {
            is SensorSectionState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is SensorSectionState.Success -> {
                val data = (sensorSectionState as SensorSectionState.Success).tricycleData
                val lastUpdated by formattedRelativeTimeFlow(data.lastUpdated?.seconds).collectAsState(initial = "")

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
                        .padding(16.dp)
                        .wrapContentHeight()
                ) {
                    SectionLabel(
                        mainLabelText = stringResource(R.string.sensors_section_title),
                        secondaryLabelText = lastUpdated,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    BatteryCard(measure = data.batteryPercentage, modifier = Modifier.fillMaxWidth())
                    SensorCard(
                        icon = R.drawable.ic_mileage,
                        name = stringResource(R.string.mileage),
                        measure = data.mileage,
                        modifier = Modifier.fillMaxWidth()
                    )
                    SensorCard(
                        icon = R.drawable.ic_battery_temp,
                        name = stringResource(R.string.battery_temp),
                        measure = data.batteryTemperature?.convertTo(TemperatureUnit.Celsius),
                        modifier = Modifier.fillMaxWidth()
                    )
                    SensorCard(
                        icon = R.drawable.ic_motor_temp,
                        name = stringResource(R.string.motor_temp),
                        measure = data.motorTemperature,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

private fun formattedRelativeTimeFlow(previousTime: Duration?) = flow {
    while (true) {
        var delayDuration: Duration = 5.seconds

        val formattedString = previousTime?.let {
            val formatter = RelativeDateTimeFormatter.getInstance()
            val currentTime = (System.currentTimeMillis() / 1000).seconds
            val relativeDifference = currentTime - it
            if (relativeDifference.inWholeSeconds < 60) {
                formatter.format(RelativeDateTimeFormatter.Direction.PLAIN, RelativeDateTimeFormatter.AbsoluteUnit.NOW)
            } else if (relativeDifference > 24.hours) {
                delayDuration = 1.days
                formatter.format(
                    relativeDifference.inWholeDays.toDouble(),
                    RelativeDateTimeFormatter.Direction.LAST,
                    RelativeDateTimeFormatter.RelativeUnit.DAYS
                )
            } else if (relativeDifference > 60.minutes) {
                delayDuration = 1.hours
                formatter.format(
                    relativeDifference.inWholeHours.toDouble(),
                    RelativeDateTimeFormatter.Direction.LAST,
                    RelativeDateTimeFormatter.RelativeUnit.HOURS
                )
            } else {
                delayDuration = 1.minutes
                formatter.format(
                    relativeDifference.inWholeMinutes.toDouble(),
                    RelativeDateTimeFormatter.Direction.LAST,
                    RelativeDateTimeFormatter.RelativeUnit.MINUTES
                )
            }
        } ?: ""
        emit(formattedString)
        delay(delayDuration)
    }
}