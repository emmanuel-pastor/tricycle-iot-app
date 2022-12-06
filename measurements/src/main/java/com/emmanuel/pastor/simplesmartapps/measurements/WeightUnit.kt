package com.emmanuel.pastor.simplesmartapps.measurements

sealed class WeightUnit(symbol: String) : MeasurementUnit(symbol) {
    object Kilogram : WeightUnit("kg")

    object Pound : WeightUnit("lb")
}