package com.emmanuel.pastor.simplesmartapps.measurements

sealed class TemperatureUnit(symbol: String) : MeasurementUnit(symbol) {
    object Celsius : TemperatureUnit("°C")
    object Fahrenheit : TemperatureUnit("°F")
    object Kelvin : TemperatureUnit("K")
}