package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/*
    * Represents the battery percentage of the tricycle.
    * The battery percentage is encoded as 1 unsigned byte.
    * The battery percentage can go from 0 to 100.
    * @throws IllegalArgumentException if the battery percentage is not in the range [0, 100]
*/
@JvmInline
value class BatteryBleEntity(val percentage: Int) {
    init {
        require(percentage in 0..100) { "Battery percentage must be in the range [0, 100]" }
    }

    companion object {
        /*
        * Uses only the first byte of the array.
        * @return null if not called with a unsigned byte array of size at least 1.
        */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): BatteryBleEntity? {
            if (byteArray.isEmpty()) return null

            return BatteryBleEntity(byteArray[0].toInt())
        }
    }
}
