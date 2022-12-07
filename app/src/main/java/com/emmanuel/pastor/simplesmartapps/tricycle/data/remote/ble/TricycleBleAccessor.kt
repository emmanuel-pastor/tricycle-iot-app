package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.BatteryBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.LoadBleEntity
import com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models.MileageBleEntity

interface TricycleBleAccessor {
    suspend fun getBatteryPercentage(): Result<BatteryBleEntity>

    suspend fun getLoad(): Result<LoadBleEntity>

    suspend fun getMileage(): Result<MileageBleEntity>
}