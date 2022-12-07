package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

/*
    * Represents the mileage of the tricycle.
    * The battery percentage is encoded as 4 unsigned bytes.
    * Mileage is in km.
*/
@JvmInline
value class MileageBleEntity(val mileage: Int) {
    companion object {
        /*
        * Uses only the first 4 bytes of the array.
        * @return null if not called with a unsigned byte array of size at least 4.
        */
        @OptIn(ExperimentalUnsignedTypes::class)
        fun fromUByteArrayOrNull(byteArray: UByteArray): MileageBleEntity? {
            if (byteArray.size < 4) return null

            return MileageBleEntity(
                byteArray[0].toInt()
                        + (byteArray[1].toInt() shl 8)
                        + (byteArray[2].toInt() shl 16)
                        + (byteArray[3].toInt() shl 24)
            )
        }
    }
}