package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.*

interface TricycleBleAccessor {
    suspend fun getBatteryPercentage(): Result<BatteryPercentageBleEntity>

    suspend fun getLoad(): Result<LoadBleEntity>

    suspend fun getMileage(): Result<MileageBleEntity>

    suspend fun getBatteryTemperature(): Result<BatteryTemperatureBleEntity>

    suspend fun getMotorTemperature(): Result<MotorTemperatureBleEntity>

    /**
     * @param diameter in centimeters
     * @throws AssertionError if the value is not in the range [0, 255]
     */
    suspend fun setWheelDiameter(diameter: Int): Result<Unit>
}