package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/**
 * Represents the mileage of the tricycle.
 * The battery percentage is encoded as 3 unsigned bytes.
 * Mileage is in kilometers.
 */
@JvmInline
value class MileageBleEntity(val mileage: Int) {
    companion object {
        /**
         * Creates a [MileageBleEntity] from a [UByteArray] or returns null if the [UByteArray] is not
         * valid.
         * Uses only the first 2 bytes of the array.alc
         * The max value that can be received is 65_535 < [Int.MAX_VALUE].
         * LSB is at index 0. MSB is at index 1.
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