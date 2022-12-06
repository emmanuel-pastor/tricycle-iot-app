package com.emmanuel.pastor.simplesmartapps.measurements

sealed class MeasurementUnit(val symbol: String) {
    override fun toString(): String {
        return symbol
    }
}