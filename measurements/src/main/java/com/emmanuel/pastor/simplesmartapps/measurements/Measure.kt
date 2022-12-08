package com.emmanuel.pastor.simplesmartapps.measurements

private sealed interface IMeasure {
    val value: Double
    val unit: MeasurementUnit
}

sealed class Measure {
    data class Distance(override val value: Double, override val unit: DistanceUnit) : IMeasure {
        fun convertTo(newUnit: DistanceUnit): Distance {
            return Distance(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Weight(override val value: Double, override val unit: WeightUnit) : IMeasure {
        fun convertTo(newUnit: WeightUnit): Weight {
            return Weight(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Speed(override val value: Double, override val unit: SpeedUnit) : IMeasure {
        fun convertTo(newUnit: SpeedUnit): Speed {
            return Speed(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Time(override val value: Double, override val unit: TimeUnit) : IMeasure {
        fun convertTo(newUnit: TimeUnit): Time {
            return Time(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    /**
     * @param ratio the proportion between 0.0 and 1.0
     */
    data class Proportion(val ratio: Double, override val unit: ProportionUnit) : IMeasure {
        override val value = ratio.coerceIn(0.0, 1.0)
    }
}