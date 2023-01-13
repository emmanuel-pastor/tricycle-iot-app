package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.*
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.TricycleGatt
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClient
import javax.inject.Inject

@OptIn(ExperimentalUnsignedTypes::class)
class TricycleBleAccessorImpl @Inject constructor(private val bleClient: BleClient) : TricycleBleAccessor {

    override suspend fun getBatteryPercentage(): Result<BatteryPercentageBleEntity> {
        var output: Result<BatteryPercentageBleEntity>? = null

        bleClient.readCharacteristic(TricycleGatt.Services.STATUS, TricycleGatt.Characteristics.BATTERY_STATE).onSuccess { bytes ->
            output = runCatching {
                val value = BatteryPercentageBleEntity.fromUByteArrayOrNull(bytes.copyOfRange(1, 2).asUByteArray())
                value ?: throw IllegalStateException("Could not parse battery percentage")
            }
        }.onFailure {
            return Result.failure(it)
        }

        return output!!
    }

    override suspend fun getLoad(): Result<LoadBleEntity> {
        return Result.failure(NotImplementedError())
    }

    override suspend fun getMileage(): Result<MileageBleEntity> {
        var output: Result<MileageBleEntity>? = null

        bleClient.readCharacteristic(TricycleGatt.Services.DATA, TricycleGatt.Characteristics.MOVEMENT_INFO).onSuccess { bytes ->
            output = runCatching {
                val value = MileageBleEntity.fromUByteArrayOrNull(
                    bytes.copyOfRange(2, 4)
                        .asUByteArray()
                        .also { it.reverse() }
                )
                value ?: throw IllegalStateException("Could not parse mileage")
            }
        }.onFailure {
            output = Result.failure(it)
        }

        return output!!
    }

    override suspend fun getBatteryTemperature(): Result<BatteryTemperatureBleEntity> {
        var output: Result<BatteryTemperatureBleEntity>? = null

        bleClient.readCharacteristic(TricycleGatt.Services.STATUS, TricycleGatt.Characteristics.BATTERY_DIAGNOSTIC).onSuccess { bytes ->
            output = runCatching {
                val value = BatteryTemperatureBleEntity.fromUByteArrayOrNull(
                    bytes.copyOfRange(1, 3)
                        .asUByteArray()
                        .also { it.reverse() }
                )
                value ?: throw IllegalStateException("Could not parse battery temperature")
            }
        }.onFailure {
            output = Result.failure(it)
        }

        return output!!
    }

    override suspend fun getMotorTemperature(): Result<MotorTemperatureBleEntity> {
        var output: Result<MotorTemperatureBleEntity>? = null

        bleClient.readCharacteristic(TricycleGatt.Services.STATUS, TricycleGatt.Characteristics.MOTOR_DIAGNOSTIC).onSuccess { bytes ->
            output = runCatching {
                val value = MotorTemperatureBleEntity.fromUByteArrayOrNull(bytes.copyOfRange(7, 8).asUByteArray())
                value ?: throw IllegalStateException("Could not parse motor temperature")
            }
        }.onFailure {
            output = Result.failure(it)
        }

        return output!!
    }
}