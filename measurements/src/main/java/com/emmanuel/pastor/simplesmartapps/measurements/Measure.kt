package com.emmanuel.pastor.simplesmartapps.measurements

import android.icu.text.MeasureFormat
import android.icu.text.MeasureFormat.FormatWidth
import android.icu.text.NumberFormat
import java.util.*

private sealed interface IMeasure {
    val value: Number
    val unit: MeasurementUnit
}

sealed class Measure : IMeasure {
    data class Distance(override val value: Number, override val unit: DistanceUnit) : Measure() {
        fun convertTo(newUnit: DistanceUnit): Distance {
            return Distance(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Weight(override val value: Number, override val unit: WeightUnit) : Measure() {
        fun convertTo(newUnit: WeightUnit): Weight {
            return Weight(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Speed(override val value: Number, override val unit: SpeedUnit) : Measure() {
        fun convertTo(newUnit: SpeedUnit): Speed {
            return Speed(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    data class Time(override val value: Number, override val unit: TimeUnit) : Measure() {
        fun convertTo(newUnit: TimeUnit): Time {
            return Time(value = unit.convertTo(value, newUnit), unit = newUnit)
        }
    }

    /**
     * @param ratio the proportion between 0.0 and 1.0
     */
    data class Proportion(val ratio: Double, override val unit: ProportionUnit) : Measure() {
        override val value = ratio.coerceIn(0.0, 1.0)
    }

    fun toL10nString(formatWidth: FormatWidth = FormatWidth.SHORT): String {
        val locale = Locale.getDefault()
        val format = MeasureFormat.getInstance(locale, formatWidth)
        return when (this) {
            is Distance -> format.format(this.toAndroidMeasure())
            is Weight -> format.format(this.toAndroidMeasure())
            is Speed -> format.format(this.toAndroidMeasure(value))
            is Time -> format.format(this.toAndroidMeasure(value))
            is Proportion -> {
                NumberFormat.getPercentInstance().format(value)
            }
        }
    }
}