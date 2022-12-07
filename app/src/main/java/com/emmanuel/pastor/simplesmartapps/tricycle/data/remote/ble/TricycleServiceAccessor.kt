package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.LoadBleEntity

@OptIn(ExperimentalUnsignedTypes::class)
interface TricycleServiceAccessor {
    suspend fun getBatteryPercentage(): Result<BatteryBleEntity>

    suspend fun getLoad(): Result<LoadBleEntity>

    suspend fun getMileage(): UByteArray
}