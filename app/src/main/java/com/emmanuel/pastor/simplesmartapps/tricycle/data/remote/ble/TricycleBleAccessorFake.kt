package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.LoadBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.MileageBleEntity
import java.nio.ByteBuffer
import javax.inject.Inject

@ExperimentalUnsignedTypes
class TricycleBleAccessorFake @Inject constructor() : TricycleBleAccessor {
    override suspend fun getBatteryPercentage(): Result<BatteryBleEntity> {
        val randomInt = getRandomIntAsUByteArray(1, maxValue = 100)

        return BatteryBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get battery percentage"))
    }

    override suspend fun getLoad(): Result<LoadBleEntity> {
        val randomInt = getRandomIntAsUByteArray(2, maxValue = 200)

        return LoadBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get load"))
    }

    override suspend fun getMileage(): Result<MileageBleEntity> {
        val randomInt = getRandomIntAsUByteArray(3, maxValue = 100_000)

        return MileageBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get mileage"))
    }

    private fun getRandomIntAsUByteArray(numberOfBytes: Int, minValue: Int = 0, maxValue: Int = Int.MAX_VALUE): UByteArray {
        return ByteBuffer.allocate(4)
            .putInt((minValue..maxValue).random()).array()
            .asUByteArray()// MSB is at index 0
            .also { it.reverse() } // LSB is at index 0
            .copyOfRange(0, numberOfBytes)
    }
}