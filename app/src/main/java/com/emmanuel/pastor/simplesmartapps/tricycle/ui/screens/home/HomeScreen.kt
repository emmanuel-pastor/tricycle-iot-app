package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emmanuel.pastor.simplesmartapps.tricycle.R
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.components.BatteryCard
import com.emmanuel.pastor.simplesmartapps.tricycle.ui.components.SensorCard

@Composable
fun HomeScreen() {
    val sensorSectionState: SensorSectionState by hiltViewModel<HomeViewModel>().sensorSectionState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (sensorSectionState) {
            is SensorSectionState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is SensorSectionState.Success -> {
                val data = (sensorSectionState as SensorSectionState.Success).tricycleData
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
                        .padding(16.dp)
                        .wrapContentHeight()
                ) {
                    BatteryCard(measure = data.batteryPercentage, modifier = Modifier.fillMaxWidth())
                    SensorCard(
                        icon = R.drawable.ic_mileage,
                        name = "Mileage",
                        measure = data.mileage,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}