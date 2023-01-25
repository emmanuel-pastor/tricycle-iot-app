package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmanuel.pastor.simplesmartapps.measurements.DistanceUnit
import com.emmanuel.pastor.simplesmartapps.measurements.Measure
import com.emmanuel.pastor.simplesmartapps.measurements.ProportionUnit
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.TricycleRepo
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.TricycleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val tricycleRepo: TricycleRepo) : ViewModel() {
    val sensorSectionState: StateFlow<SensorSectionState> = tricycleRepo.fetchTricycleData().map {
        SensorSectionState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SensorSectionState.Loading
    )

    val wearSectionState: StateFlow<WearSectionState> = sensorSectionState.map {
        when (sensorSectionState.value) {
            is SensorSectionState.Loading -> WearSectionState.Loading
            is SensorSectionState.Success -> {
                val mileage =
                    (sensorSectionState.value as SensorSectionState.Success).tricycleData.mileage ?: Measure.Distance(0.0, DistanceUnit.Kilometer)
                WearSectionState.Success(
                    TricycleWear(
                        tiresWear = computeTiresWear(mileage),
                        brakesWear = computeBrakesWear(mileage),
                        batteryWear = computeBatteryWear(mileage)
                    )
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WearSectionState.Loading
    )

    private fun computeTiresWear(mileage: Measure.Distance): Wear {
        val tiresLifespan = Measure.Distance(5_000.0, DistanceUnit.Kilometer)
        val percentage = Measure.Proportion(mileage.coerceIn(tiresLifespan) / tiresLifespan, ProportionUnit.Percent)
        val replaceIn = tiresLifespan - mileage.coerceIn(tiresLifespan)
        return Wear(
            percentage = percentage,
            replaceIn = replaceIn
        )
    }

    private fun computeBrakesWear(mileage: Measure.Distance): Wear {
        val brakesLifespan = Measure.Distance(1_200.0, DistanceUnit.Kilometer)
        val percentage = Measure.Proportion(mileage.coerceIn(brakesLifespan) / brakesLifespan, ProportionUnit.Percent)
        val replaceIn = brakesLifespan - mileage.coerceIn(brakesLifespan)
        return Wear(
            percentage = percentage,
            replaceIn = replaceIn
        )
    }

    private fun computeBatteryWear(mileage: Measure.Distance): Wear {
        val batteryLifespan = Measure.Distance(50_000.0, DistanceUnit.Kilometer)
        val percentage = Measure.Proportion(mileage.coerceIn(batteryLifespan) / batteryLifespan, ProportionUnit.Percent)
        val replaceIn = batteryLifespan - mileage.coerceIn(batteryLifespan)
        return Wear(
            percentage = percentage,
            replaceIn = replaceIn
        )
    }

    fun Measure.Distance.coerceIn(max: Measure.Distance): Measure.Distance {
        return Measure.Distance(
            value = this.value.toDouble().coerceIn(0.0, max.value.toDouble()),
            unit = this.unit
        )
    }
}

sealed interface SensorSectionState {
    object Loading : SensorSectionState
    data class Success(
        val tricycleData: TricycleData
    ) : SensorSectionState
}

sealed interface WearSectionState {
    object Loading : WearSectionState
    data class Success(
        val sensorSectionState: TricycleWear
    ) : WearSectionState
}

data class TricycleWear(val tiresWear: Wear, val brakesWear: Wear, val batteryWear: Wear)

data class Wear(val percentage: Measure.Proportion, val replaceIn: Measure.Distance)