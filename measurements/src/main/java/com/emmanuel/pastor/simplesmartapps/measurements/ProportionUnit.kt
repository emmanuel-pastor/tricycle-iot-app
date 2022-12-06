package com.emmanuel.pastor.simplesmartapps.measurements

sealed class ProportionUnit(symbol: String) : MeasurementUnit(symbol) {
    object Percent : ProportionUnit("%")
}