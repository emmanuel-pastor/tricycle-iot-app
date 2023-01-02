package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.LoadBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.MileageBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClient
import javax.inject.Inject

@OptIn(ExperimentalUnsignedTypes::class)
class TricycleBleAccessorImpl @Inject constructor(private val bleClient: BleClient) : TricycleBleAccessor {

    override suspend fun getBatteryPercentage(): Result<BatteryBleEntity> {
        var output: Result<BatteryBleEntity>? = null

        bleClient.readCharacteristic("1161", "3300").onSuccess { bytes ->
            val value = BatteryBleEntity.fromUByteArrayOrNull(bytes.copyOfRange(1, 2).asUByteArray())
            output = if (value != null) {
                Result.success(value)
            } else {
                Result.failure(IllegalStateException("Could not parse battery percentage"))
            }
        }.onFailure {
            output = Result.failure(it)
        }

        return output!!
    }

    override suspend fun getLoad(): Result<LoadBleEntity> {
        return Result.failure(NotImplementedError())
    }

    override suspend fun getMileage(): Result<MileageBleEntity> {
        var output: Result<MileageBleEntity>? = null

        bleClient.readCharacteristic("1163", "5500").onSuccess { bytes ->
            val value = MileageBleEntity.fromUByteArrayOrNull(
                bytes.copyOfRange(2, 4)
                    .asUByteArray()
                    .also { it.reverse() }
            )

            output = if (value != null) {
                Result.success(value)
            } else {
                Result.failure(IllegalStateException("Could not parse mileage"))
            }
        }.onFailure {
            output = Result.failure(it)
        }

        return output!!
    }
}