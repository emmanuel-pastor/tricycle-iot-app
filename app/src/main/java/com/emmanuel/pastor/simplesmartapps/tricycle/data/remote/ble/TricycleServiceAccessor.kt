package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity

@OptIn(ExperimentalUnsignedTypes::class)
interface TricycleServiceAccessor {
    suspend fun getBatteryPercentage(): Result<BatteryBleEntity>

    suspend fun getLoad(): UByteArray

    suspend fun getMileage(): UByteArray
}