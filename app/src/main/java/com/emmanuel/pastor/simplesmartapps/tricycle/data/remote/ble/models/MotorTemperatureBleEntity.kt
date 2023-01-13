package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/**
 * Temperature is in Celsius.
 */
@JvmInline
value class MotorTemperatureBleEntity(val temperature: Int) {

    companion object {
        /**
         * Creates a [MotorTemperatureBleEntity] from a [UByteArray] or returns null if the [UByteArray] is not
         * valid.
         *
         * Uses only the first byte of the array.
         *
         * The max value that can be received is 255 < [Int.MAX_VALUE].
         * @return null if not called with a [UByteArray] of size at least 1.
         */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): MotorTemperatureBleEntity? {
            if (byteArray.isEmpty()) return null

            return MotorTemperatureBleEntity(byteArray[0].toInt())
        }
    }
}