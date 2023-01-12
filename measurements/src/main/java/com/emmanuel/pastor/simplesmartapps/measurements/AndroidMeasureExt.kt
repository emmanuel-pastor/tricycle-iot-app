package com.emmanuel.pastor.simplesmartapps.measurements

internal fun Measure.Distance.toAndroidMeasure(): android.icu.util.Measure {
    val androidUnit = when (unit) {
        DistanceUnit.Kilometer -> android.icu.util.MeasureUnit.KILOMETER
        DistanceUnit.Meter -> android.icu.util.MeasureUnit.METER
        DistanceUnit.Mile -> android.icu.util.MeasureUnit.MILE
        DistanceUnit.Yard -> android.icu.util.MeasureUnit.YARD
        DistanceUnit.Foot -> android.icu.util.MeasureUnit.FOOT
    }
    return android.icu.util.Measure(value, androidUnit)
}

internal fun Measure.Weight.toAndroidMeasure(): android.icu.util.Measure {
    val androidUnit = when (unit) {
        WeightUnit.Kilogram -> android.icu.util.MeasureUnit.KILOGRAM
        WeightUnit.Pound -> android.icu.util.MeasureUnit.POUND
    }
    return android.icu.util.Measure(value, androidUnit)
}

internal fun Measure.Speed.toAndroidMeasure(value: Number): android.icu.util.Measure {
    val androidUnit = when (unit) {
        is SpeedUnit.KilometerPerHour -> android.icu.util.MeasureUnit.KILOMETER_PER_HOUR
        is SpeedUnit.MeterPerSecond -> android.icu.util.MeasureUnit.METER_PER_SECOND
        is SpeedUnit.MilePerHour -> android.icu.util.MeasureUnit.MILE_PER_HOUR
        is SpeedUnit.FootPerSecond -> android.icu.util.MeasureUnit.MILE_PER_HOUR
    }
    return if (unit is SpeedUnit.FootPerSecond) android.icu.util.Measure(
        unit.convertTo(value, SpeedUnit.MilePerHour),
        androidUnit
    ) else android.icu.util.Measure(
        value,
        androidUnit
    )
}

internal fun Measure.Time.toAndroidMeasure(value: Number): android.icu.util.Measure {
    val androidUnit = when (unit) {
        is TimeUnit.Hour -> android.icu.util.MeasureUnit.HOUR
        is TimeUnit.Minute -> android.icu.util.MeasureUnit.MINUTE
        is TimeUnit.Second -> android.icu.util.MeasureUnit.SECOND
    }
    return android.icu.util.Measure(value, androidUnit)
}

internal fun Measure.Temperature.toAndroidMeasure(value: Number): android.icu.util.Measure {
    val androidUnit = when (unit) {
        is TemperatureUnit.Celsius -> android.icu.util.MeasureUnit.CELSIUS
        is TemperatureUnit.Fahrenheit -> android.icu.util.MeasureUnit.FAHRENHEIT
        is TemperatureUnit.Kelvin -> android.icu.util.MeasureUnit.KELVIN
    }
    return android.icu.util.Measure(value, androidUnit)
}