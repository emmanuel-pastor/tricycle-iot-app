package com.emmanuel.pastor.simplesmartapps.tricycle.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.emmanuel.pastor.simplesmartapps.measurements.Measure
import com.emmanuel.pastor.simplesmartapps.tricycle.R

@Composable
fun BatteryCard(modifier: Modifier = Modifier, measure: Measure.Proportion?) {
    val batteryIcon = measure?.let {
        when (it.value) {
            in 0.0..0.12 -> R.drawable.ic_battery_0_7
            in 0.13..0.25 -> R.drawable.ic_battery_1_7
            in 0.26..0.37 -> R.drawable.ic_battery_2_7
            in 0.38..0.5 -> R.drawable.ic_battery_3_7
            in 0.51..0.62 -> R.drawable.ic_battery_4_7
            in 0.63..0.75 -> R.drawable.ic_battery_5_7
            in 0.76..0.87 -> R.drawable.ic_battery_6_7
            in 0.88..1.0 -> R.drawable.ic_battery_7_7
            else -> R.drawable.ic_battery_unknown
        }
    } ?: R.drawable.ic_battery_unknown
    SensorCard(icon = batteryIcon, name = stringResource(R.string.battery), measure = measure, modifier = modifier)
}