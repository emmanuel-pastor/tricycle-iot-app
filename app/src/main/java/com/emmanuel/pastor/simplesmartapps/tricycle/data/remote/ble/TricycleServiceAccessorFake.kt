package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import java.nio.ByteBuffer

@ExperimentalUnsignedTypes
class TricycleServiceAccessorFake : TricycleServiceAccessor {
    override suspend fun getBatteryPercentage(): UByteArray {
        return getRandomIntAsUByteArray(1, 100)
    }

    override suspend fun getLoad(): UByteArray {
        return getRandomIntAsUByteArray(2, 200)
    }

    override suspend fun getMileage(): UByteArray {
        return getRandomIntAsUByteArray(4, 100_000)
    }

    private fun getRandomIntAsUByteArray(numberOfBytes: Int, minValue: Int = 0, maxValue: Int = Int.MAX_VALUE): UByteArray {
        return ByteBuffer.allocate(4)
            .putInt((minValue..maxValue).random()).array()
            .asUByteArray()
            .copyOfRange(0, numberOfBytes)
    }
}