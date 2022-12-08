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
         * Uses only the first 3 bytes of the array.
         * The max value that can be received is 16_777_215 < [Int.MAX_VALUE].
         * @return null if not called with a [UByteArray] of size at least 3.
         */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): MileageBleEntity? {
            if (byteArray.size < 3) return null

            return MileageBleEntity(
                byteArray[0].toInt()
                        + (byteArray[1].toInt() shl 8)
                        + (byteArray[2].toInt() shl 16)
            )
        }
    }
}