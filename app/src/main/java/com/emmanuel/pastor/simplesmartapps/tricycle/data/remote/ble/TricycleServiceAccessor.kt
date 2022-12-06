package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

interface TricycleServiceAccessor {
    suspend fun getBatteryPercentage(): ByteArray

    suspend fun getLoad(): ByteArray

    suspend fun getMileage(): ByteArray
}