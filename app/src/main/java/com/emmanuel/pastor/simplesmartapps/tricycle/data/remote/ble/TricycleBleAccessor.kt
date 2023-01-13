package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.*

interface TricycleBleAccessor {
    suspend fun getBatteryPercentage(): Result<BatteryPercentageBleEntity>

    suspend fun getLoad(): Result<LoadBleEntity>

    suspend fun getMileage(): Result<MileageBleEntity>

    suspend fun getBatteryTemperature(): Result<BatteryTemperatureBleEntity>

    suspend fun getMotorTemperature(): Result<MotorTemperatureBleEntity>
}