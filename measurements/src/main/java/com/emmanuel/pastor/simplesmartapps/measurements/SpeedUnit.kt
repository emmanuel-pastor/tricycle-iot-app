package com.emmanuel.pastor.simplesmartapps.measurements

sealed class SpeedUnit(symbol: String) : MeasurementUnit(symbol) {
    object KilometerPerHour : SpeedUnit("km/h")
    object MeterPerSecond : SpeedUnit("m/s")

    object MilePerHour : SpeedUnit("mph")
    object FootPerSecond : SpeedUnit("ft/s")
}