package com.emmanuel.pastor.simplesmartapps.tricycle.domain.models

import com.emmanuel.pastor.simplesmartapps.measurements.*
import com.emmanuel.pastor.simplesmartapps.tricycle.data.local.data_store.TricycleDsEntity

/**
 * @param batteryPercentage the battery percentage of the tricycle
 * @param load the load weight of the tricycle
 * @param mileage the mileage of the tricycle
 * @param batteryTemperature the battery temperature of the tricycle
 * @param motorTemperature the motor temperature of the tricycle
 * @param lastUpdated the last time the tricycle data was updated in a seconds timestamp
 */
data class TricycleData(
    val batteryPercentage: Measure.Proportion?,
    val load: Measure.Weight?,
    val mileage: Measure.Distance?,
    val batteryTemperature: Measure.Temperature?,
    val motorTemperature: Measure.Temperature?,
    val lastUpdated: Long?
)

fun TricycleDsEntity.toTricycleData(): TricycleData =
    TricycleData(
        batteryPercentage = this.batteryPercentage?.let { Measure.Proportion(it / 100.0, ProportionUnit.Percent) },
        load = this.load?.let { Measure.Weight(it.toDouble(), WeightUnit.Kilogram) },
        mileage = this.mileage?.let { Measure.Distance(it.toDouble(), DistanceUnit.Kilometer) },
        batteryTemperature = this.batteryTemperature?.let { Measure.Temperature(it.toDouble() / 10, TemperatureUnit.Kelvin) },
        motorTemperature = this.motorTemperature?.let { Measure.Temperature(it.toDouble(), TemperatureUnit.Celsius) },
        lastUpdated = this.lastUpdated
    )
