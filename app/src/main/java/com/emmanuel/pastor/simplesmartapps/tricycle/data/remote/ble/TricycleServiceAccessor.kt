package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

@OptIn(ExperimentalUnsignedTypes::class)
interface TricycleServiceAccessor {
    suspend fun getBatteryPercentage(): UByteArray

    suspend fun getLoad(): UByteArray

    suspend fun getMileage(): UByteArray
}