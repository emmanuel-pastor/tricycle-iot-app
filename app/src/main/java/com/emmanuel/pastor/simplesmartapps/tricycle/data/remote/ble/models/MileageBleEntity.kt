package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/**
 * Mileage is in kilometers.
 */
@JvmInline
value class MileageBleEntity(val mileage: Int) {
    companion object {
        /**
         * Creates a [MileageBleEntity] from a [UByteArray] or returns null if the [UByteArray] is not
         * valid.
         *
         * Uses only the first 2 bytes of the array.
         *
         * LSB should be at index 0
         *
         * MSB should be at index 1
         *
         * The max value that can be received is 65_535 < [Int.MAX_VALUE].
         * @return null if not called with a [UByteArray] of size at least 2.
         */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): MileageBleEntity? {
            if (byteArray.size < 2) return null

            return MileageBleEntity(
                byteArray[0].toInt() + (byteArray[1].toInt() shl 8)
            )
        }
    }
}