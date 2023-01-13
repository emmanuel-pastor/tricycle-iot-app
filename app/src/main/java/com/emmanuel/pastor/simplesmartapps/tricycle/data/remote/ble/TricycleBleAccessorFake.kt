package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.*
import java.nio.ByteBuffer
import javax.inject.Inject

@ExperimentalUnsignedTypes
class TricycleBleAccessorFake @Inject constructor() : TricycleBleAccessor {
    override suspend fun getBatteryPercentage(): Result<BatteryPercentageBleEntity> {
        val randomInt = getRandomIntAsUByteArray(1, maxValue = 100)

        return BatteryPercentageBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get battery percentage"))
    }

    override suspend fun getLoad(): Result<LoadBleEntity> {
        val randomInt = getRandomIntAsUByteArray(2, maxValue = 200)

        return LoadBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get load"))
    }

    override suspend fun getMileage(): Result<MileageBleEntity> {
        val randomInt = getRandomIntAsUByteArray(2, maxValue = 65_535)

        return MileageBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get mileage"))
    }

    // In 0.1 degrees Kelvin
    override suspend fun getBatteryTemperature(): Result<BatteryTemperatureBleEntity> {
        val randomInt = getRandomIntAsUByteArray(2, minValue = 2680, maxValue = 3330)

        return BatteryTemperatureBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get battery temperature"))
    }

    // In Celsius
    override suspend fun getMotorTemperature(): Result<MotorTemperatureBleEntity> {
        val randomInt = getRandomIntAsUByteArray(1, maxValue = 180)

        return MotorTemperatureBleEntity.fromUByteArrayOrNull(randomInt)?.let { Result.success(it) }
            ?: Result.failure(IllegalStateException("Could not get motor temperature"))
    }

    private fun getRandomIntAsUByteArray(numberOfBytes: Int, minValue: Int = 0, maxValue: Int = Int.MAX_VALUE): UByteArray {
        return ByteBuffer.allocate(4)
            .putInt((minValue..maxValue).random()).array()
            .asUByteArray()// MSB is at index 0
            .also { it.reverse() } // LSB is at index 0
            .copyOfRange(0, numberOfBytes)
    }
}