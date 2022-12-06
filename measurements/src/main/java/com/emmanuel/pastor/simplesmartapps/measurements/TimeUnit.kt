package com.emmanuel.pastor.simplesmartapps.measurements

sealed class TimeUnit(symbol: String) : MeasurementUnit(symbol) {
    object Hour : TimeUnit("h")
    object Minute : TimeUnit("m")
    object Second : TimeUnit("s")
}