package com.emmanuel.pastor.simplesmartapps.measurements

internal fun DistanceUnit.convertTo(inputValue: Number, to: DistanceUnit): Number {
    val value = inputValue.toDouble()
    return when (this) {
        is DistanceUnit.Kilometer -> when (to) {
            is DistanceUnit.Kilometer -> value
            is DistanceUnit.Meter -> value * 1000
            is DistanceUnit.Mile -> value * 0.621371
            is DistanceUnit.Yard -> value * 1093.61
            is DistanceUnit.Foot -> value * 3280.84
        }
        is DistanceUnit.Meter -> when (to) {
            is DistanceUnit.Kilometer -> value / 1000.0
            is DistanceUnit.Meter -> value
            is DistanceUnit.Mile -> value * 0.000621371
            is DistanceUnit.Yard -> value * 1.09361
            is DistanceUnit.Foot -> value * 3.28084
        }
        is DistanceUnit.Mile -> when (to) {
            is DistanceUnit.Kilometer -> value * 1.60934
            is DistanceUnit.Meter -> value * 1609.34
            is DistanceUnit.Mile -> value
            is DistanceUnit.Yard -> value * 1760.0
            is DistanceUnit.Foot -> value * 5280.0
        }
        is DistanceUnit.Yard -> when (to) {
            is DistanceUnit.Kilometer -> value * 0.0009144
            is DistanceUnit.Meter -> value * 0.9144
            is DistanceUnit.Mile -> value * 0.000568182
            is DistanceUnit.Yard -> value
            is DistanceUnit.Foot -> value * 3.0
        }
        is DistanceUnit.Foot -> when (to) {
            is DistanceUnit.Kilometer -> value * 0.0003048
            is DistanceUnit.Meter -> value * 0.3048
            is DistanceUnit.Mile -> value * 0.000189394
            is DistanceUnit.Yard -> value * 0.333333
            is DistanceUnit.Foot -> value
        }
    }
}

internal fun WeightUnit.convertTo(inputValue: Number, to: WeightUnit): Number {
    val value = inputValue.toDouble()
    return when (this) {
        is WeightUnit.Kilogram -> when (to) {
            is WeightUnit.Kilogram -> value
            is WeightUnit.Pound -> value * 2.20462
        }
        is WeightUnit.Pound -> when (to) {
            is WeightUnit.Kilogram -> value * 0.453592
            is WeightUnit.Pound -> value
        }
    }
}

internal fun SpeedUnit.convertTo(inputValue: Number, to: SpeedUnit): Number {
    val value = inputValue.toDouble()
    return when (this) {
        is SpeedUnit.KilometerPerHour -> when (to) {
            is SpeedUnit.KilometerPerHour -> value
            is SpeedUnit.MeterPerSecond -> value * 0.277778
            is SpeedUnit.MilePerHour -> value * 0.621371
            is SpeedUnit.FootPerSecond -> value * 0.911344
        }
        is SpeedUnit.MeterPerSecond -> when (to) {
            is SpeedUnit.KilometerPerHour
            -> value * 3.6
            is SpeedUnit.MeterPerSecond -> value
            is SpeedUnit.MilePerHour -> value * 2.23694
            is SpeedUnit.FootPerSecond -> value * 3.28084
        }
        is SpeedUnit.MilePerHour -> when (to) {
            is SpeedUnit.KilometerPerHour -> value * 1.60934
            is SpeedUnit.MeterPerSecond -> value * 0.44704
            is SpeedUnit.MilePerHour -> value
            is SpeedUnit.FootPerSecond -> value * 1.46667
        }
        is SpeedUnit.FootPerSecond -> when (to) {
            is SpeedUnit.KilometerPerHour
            -> value * 1.09728
            is SpeedUnit.MeterPerSecond -> value * 0.3048
            is SpeedUnit.MilePerHour -> value * 0.681818
            is SpeedUnit.FootPerSecond -> value
        }
    }
}

internal fun TimeUnit.convertTo(inputValue: Number, to: TimeUnit): Number {
    val value = inputValue.toDouble()
    return when (this) {
        is TimeUnit.Hour -> when (to) {
            is TimeUnit.Hour -> value
            is TimeUnit.Minute -> value * 60.0
            is TimeUnit.Second -> value * 3600.0
        }
        is TimeUnit.Minute -> when (to) {
            is TimeUnit.Hour -> value / 60.0
            is TimeUnit.Minute -> value
            is TimeUnit.Second -> value * 60.0
        }
        is TimeUnit.Second -> when (to) {
            is TimeUnit.Hour -> value / 3600.0
            is TimeUnit.Minute -> value / 60.0
            is TimeUnit.Second -> value
        }
    }
}