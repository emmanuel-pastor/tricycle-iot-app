package com.emmanuel.pastor.simplesmartapps.tricycle.domain.models

object TricycleGatt {
    object Services {
        const val SCANNING = "1840"
        const val STATUS = "1161"
        const val DATA = "1163"
    }

    object Characteristics {
        // Service: STATUS
        const val BATTERY_STATE = "3300"

        // Service: DATA
        const val MOVEMENT_INFO = "5500"
    }
}