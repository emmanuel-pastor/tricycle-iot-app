package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import java.nio.ByteBuffer

class TricycleServiceAccessorFake : TricycleServiceAccessor {
    override suspend fun getBatteryPercentage(): ByteArray {
        return getRandomIntAsByteArray(1, 100)
    }

    override suspend fun getLoad(): ByteArray {
        return getRandomIntAsByteArray(2, 200)
    }

    override suspend fun getMileage(): ByteArray {
        return getRandomIntAsByteArray(4, 100_000)
    }

    private fun getRandomIntAsByteArray(numberOfBytes: Int, minValue: Int = 0, maxValue: Int = Int.MAX_VALUE): ByteArray {
        return ByteBuffer.allocate(numberOfBytes).putInt((minValue..maxValue).random()).array()
    }
}