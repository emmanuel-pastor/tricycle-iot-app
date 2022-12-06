package com.emmanuel.pastor.simplesmartapps.measurements

private sealed interface IMeasure {
    val value: Double
    val unit: MeasurementUnit
    val timestamp: Long
}

sealed class Measure {
    data class Distance(override val value: Double, override val unit: DistanceUnit, override val timestamp: Long) : IMeasure {
        fun convertTo(newUnit: DistanceUnit): Distance {
            return Distance(value = unit.convertTo(value, newUnit), unit = newUnit, timestamp = timestamp)
        }
    }

    data class Weight(override val value: Double, override val unit: WeightUnit, override val timestamp: Long) : IMeasure {
        fun convertTo(newUnit: WeightUnit): Weight {
            return Weight(value = unit.convertTo(value, newUnit), unit = newUnit, timestamp = timestamp)
        }
    }

    data class Speed(override val value: Double, override val unit: SpeedUnit, override val timestamp: Long) : IMeasure {
        fun convertTo(newUnit: SpeedUnit): Speed {
            return Speed(value = unit.convertTo(value, newUnit), unit = newUnit, timestamp = timestamp)
        }
    }

    data class Time(override val value: Double, override val unit: TimeUnit, override val timestamp: Long) : IMeasure {
        fun convertTo(newUnit: TimeUnit): Time {
            return Time(value = unit.convertTo(value, newUnit), unit = newUnit, timestamp = timestamp)
        }
    }

    data class Proportion(val ratio: Double, override val unit: ProportionUnit, override val timestamp: Long) : IMeasure {
        override val value = ratio.coerceIn(0.0, 1.0)
    }
}