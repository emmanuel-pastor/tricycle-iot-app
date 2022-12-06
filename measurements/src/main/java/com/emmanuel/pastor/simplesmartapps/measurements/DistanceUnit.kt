package com.emmanuel.pastor.simplesmartapps.measurements

sealed class DistanceUnit(symbol: String) : MeasurementUnit(symbol) {
    object Kilometer : DistanceUnit("km")
    object Meter : DistanceUnit("m")

    object Mile : DistanceUnit("mi")
    object Yard : DistanceUnit("yd")
    object Foot : DistanceUnit("ft")
}