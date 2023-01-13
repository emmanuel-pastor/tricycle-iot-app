package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/**
 * The battery percentage can go from 0 to 100.
 * @throws IllegalArgumentException if the battery percentage is not in the range [0, 100]
 */
@JvmInline
value class BatteryPercentageBleEntity(val percentage: Int) {
    init {
        require(percentage in 0..100) { "Battery percentage must be in the range [0, 100]" }
    }

    companion object {
        /**
         * Creates a [BatteryPercentageBleEntity] from a [UByteArray] or returns null if the [UByteArray] is not
         * valid.
         *
         * Uses only the first byte of the array.
         *
         * The max value that can be received is 255 < [Int.MAX_VALUE].
         * @return null if not called with a [UByteArray] of size at least 1.
         */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): BatteryPercentageBleEntity? {
            if (byteArray.isEmpty()) return null

            return BatteryPercentageBleEntity(byteArray[0].toInt())
        }
    }
}
