package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/**
 * Represents the weight of the load on the tricycle.
 * The load is encoded as 2 unsigned bytes.
 * Weight is in kilograms.
 */
@JvmInline
value class LoadBleEntity(val load: Int) {
    companion object {
        /**
         * Creates a [LoadBleEntity] from a [UByteArray] or returns null if the [UByteArray] is not
         * valid.
         * Uses only the first 2 bytes of the array.
         * The max value that can be received is 65_535 < [Int.MAX_VALUE].
         * @return null if not called with a [UByteArray] of size at least 2.
         */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): LoadBleEntity? {
            if (byteArray.size < 2) return null

            return LoadBleEntity(byteArray[0].toInt() + (byteArray[1].toInt() shl 8))
        }
    }
}