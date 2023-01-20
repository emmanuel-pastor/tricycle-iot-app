package com.emmanuel.pastor.simplesmartapps.tricycle.domain.models

object TricycleGatt {
    object Services {
        const val SCANNING = "1840"
        const val SETTINGS = "1162"
        const val STATUS = "1161"
        const val DATA = "1163"
    }

    object Characteristics {
        // Service: SETTINGS
        const val SET_BIKE_SETTINGS = "4400"

        // Service: STATUS
        const val BATTERY_STATE = "3300"
        const val BATTERY_DIAGNOSTIC = "3301"
        const val MOTOR_DIAGNOSTIC = "3302"

        // Service: DATA
        const val MOVEMENT_INFO = "5500"
    }
}