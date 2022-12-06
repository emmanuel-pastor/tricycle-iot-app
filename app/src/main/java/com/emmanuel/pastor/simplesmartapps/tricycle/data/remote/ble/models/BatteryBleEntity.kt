package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

@JvmInline
/*
    * Represents the battery percentage of the tricycle.
    * The battery percentage is encoded as 1 unsigned byte.
    * The battery percentage can go from 0 to 100.
*/
value class BatteryBleEntity(val percentage: Int) {
    companion object {
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArray(byteArray: UByteArray): BatteryBleEntity {
            return BatteryBleEntity(byteArray[0].toInt())
        }
    }
}
