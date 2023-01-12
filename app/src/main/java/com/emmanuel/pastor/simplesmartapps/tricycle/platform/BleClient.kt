package com.emmanuel.pastor.simplesmartapps.tricycle.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface BleClient {
    /**
     * @param tricycleSerialNumber Your tricycle is named E-BIKE_XXXXX_BIKE_NAME where XXXXX are the 10 hex characters
     * representing your tricycle serial number.
     * @param timeout The time to wait to both find the device and connect to it. Default is 10 seconds.
     * @throws SecurityException if the app does not have the required permissions.
     * @throws IllegalStateException if the Bluetooth is not enabled.
     * @throws DeviceNotFoundException if the device was not found.
     * @throws ConnectionFailedException if an error occurs during the connection phase.
     */
    suspend fun openConnection(tricycleSerialNumber: String, timeout: Duration = 10.seconds): Result<Unit>

    /**
     * @throws IllegalStateException if the Bluetooth is not enabled.
     * @throws SecurityException if the app does not have the required permissions.
     */
    fun closeConnection(): Result<Unit>

    /**
     * @param serviceUuid 4 hex characters representing the 16 bits UUID that identifies the service
     * @param characteristicUuid 4 hex characters representing the 16 bits UUID that identifies the characteristic
     * @throws IllegalStateException on internal errors like device not connected.
     * @throws RuntimeException if the characteristic could not be read
     */
    suspend fun readCharacteristic(serviceUuid: String, characteristicUuid: String): Result<ByteArray>

    fun subscribeToCharacteristic(uuid: String): Flow<ByteArray>

    val isConnectedStateFlow: StateFlow<Boolean>
}