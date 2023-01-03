package com.emmanuel.pastor.simplesmartapps.tricycle.platform

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration

@SuppressWarnings("MissingPermission")
class BleClientImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val adapter: BluetoothAdapter
) : BleClient {
    companion object {
        private const val TAG = "BleClient"

        private object GattServices {
            const val STATUS = "1161"
        }

        private object GattCharacteristics {
            const val GET_BATTERY_STATE = "3300"
        }
    }

    private sealed class GattAction {
        data class ReadCharacteristic(val serviceUuid: String, val characteristicUuid: String) : GattAction()
    }

    private sealed class GattEvent {
        object ServicesDiscovered : GattEvent()
        data class CharacteristicRead(val result: Result<ByteArray>) : GattEvent()
    }

    private val bluetoothLeScanner = adapter.bluetoothLeScanner

    private var scanCallback: ScanCallback? = null
    private var gatt: BluetoothGatt? = null

    private val actionChannel = Channel<GattAction>(capacity = Channel.UNLIMITED)
    private val eventChannel = Channel<GattEvent>(capacity = Channel.CONFLATED)
    private val ackChannel = Channel<Boolean>()

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    this@BleClientImpl.gatt = gatt
                    _isConnectedStateFlow.value = true
                    Log.d(TAG, "Connected to GATT server.")
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    _isConnectedStateFlow.value = false
                    closeConnection()
                    Log.d(TAG, "Disconnected from GATT server.")
                }
            } else {
                Log.e(TAG, "Error $status encountered for device ${gatt?.device?.name}. Disconnecting...")
                closeConnection()
                _isConnectedStateFlow.value = false
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            eventChannel.trySend(GattEvent.ServicesDiscovered)
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray, status: Int) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        eventChannel.trySend(GattEvent.CharacteristicRead(Result.success(value)))
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        eventChannel.trySend(GattEvent.CharacteristicRead(Result.failure(RuntimeException("Read not permitted for $uuid!"))))
                    }
                    else -> {
                        eventChannel.trySend(GattEvent.CharacteristicRead(Result.failure(RuntimeException("Characteristic read failed for $uuid, error: $status"))))
                    }
                }
            }
        }
    }

    init {
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            actionChannel.consumeEach { action ->
                when (action) {
                    is GattAction.ReadCharacteristic -> {
                        Log.d(TAG, "Read Action received")
                        readCharacteristicAction(action.serviceUuid, action.characteristicUuid)
                        Log.d(TAG, "Waiting for ACK")
                        ackChannel.receive()
                        Log.d(TAG, "ACK received")
                    }
                }
            }
        }
    }

    override suspend fun openConnection(tricycleSerialNumber: String, timeout: Duration): Result<Unit> = runCatching {
        val scanTimeout = timeout * 0.75
        val connectionTimeout = timeout - scanTimeout

        val device = scanForDevice(tricycleSerialNumber, scanTimeout)

        device.connectGatt(appContext, false, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
        withTimeout(connectionTimeout) {
            Log.d(TAG, "Waiting for connection...")
            _isConnectedStateFlow.first { it }
            return@withTimeout
        }

        Log.d(TAG, "Discovering services...")
        this.gatt?.discoverServices()
        eventChannel.receive()
        Log.d(TAG, "Services discovered")

        // Read an encrypted characteristic so bonding is started automatically by Android
        readCharacteristic(GattServices.STATUS, GattCharacteristics.GET_BATTERY_STATE)
            .onSuccess {
                Log.d(TAG, "Characteristic read: ${it.contentToString()}")
            }
            .onFailure {
                Log.d(TAG, it.toString())
            }
    }

    /**
     * @throws SecurityException if the app does not have the required permissions.
     * @throws IllegalStateException if the Bluetooth is not enabled.
     * @throws DeviceNotFoundException if the device was not found.
     * @return The device that was found.
     */
    private suspend inline fun scanForDevice(tricycleSerialNumber: String, timeout: Duration): BluetoothDevice {
        if (!adapter.isEnabled) {
            throw IllegalStateException("Bluetooth is not enabled")
        }

        return try {
            scanForDeviceSuspendWithTimeout(tricycleSerialNumber, timeout)
        } catch (e: SecurityException) {
            throw e
        } catch (e: Exception) {
            throw DeviceNotFoundException("Could not find device with serial number $tricycleSerialNumber", e)
        } finally {
            try {
                bluetoothLeScanner.stopScan(scanCallback)
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping scan", e)
            } finally {
                scanCallback = null
            }
        }
    }

    private suspend inline fun scanForDeviceSuspendWithTimeout(
        tricycleSerialNumber: String,
        timeout: Duration
    ): BluetoothDevice =
        suspendCoroutineWithTimeout(timeout) { continuation ->
            scanCallback = object : ScanCallback() {
                override fun onScanResult(callbackType: Int, result: ScanResult) {
                    super.onScanResult(callbackType, result)
                    if (continuation.isActive) {
                        val regex = Regex("E-BIKE_$tricycleSerialNumber.*")
                        if (result.device.name?.matches(regex) == true) {
                            continuation.resumeWith(Result.success(result.device))
                        }
                    }
                }

                override fun onScanFailed(errorCode: Int) {
                    super.onScanFailed(errorCode)
                    if (continuation.isActive) {
                        continuation.resumeWith(Result.failure(IllegalStateException("Scan failed with error code $errorCode")))
                    }
                }
            }

            val statusServiceUuidAndroid = ParcelUuid.fromString("0000${GattServices.STATUS}-0000-1000-8000-00805f9b34fb")
            val filters =
                mutableListOf<ScanFilter>(
                    ScanFilter.Builder()
                        .setServiceUuid(statusServiceUuidAndroid)
                        .build()
                )

            val setting =
                ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build()
            bluetoothLeScanner.startScan(filters, setting, scanCallback)
        }

    override fun closeConnection(): Result<Unit> = runCatching {
        Log.d(TAG, "Closing GATT connection")
        gatt?.let {
            it.close()
            gatt = null
            _isConnectedStateFlow.value = false
        }
    }

    override suspend fun readCharacteristic(serviceUuid: String, characteristicUuid: String): Result<ByteArray> {
        Log.d(TAG, "Sending Read Action")
        actionChannel.trySend(GattAction.ReadCharacteristic(serviceUuid, characteristicUuid))
        Log.d(TAG, "Waiting for Read Event")
        val event = eventChannel.receive()
        Log.d(TAG, "Read Event received")
        Log.d(TAG, "Sending ACK")
        ackChannel.trySend(true)

        return if (event is GattEvent.CharacteristicRead) {
            event.result
        } else {
            Result.failure(IllegalStateException("Event channel is de-synchronized with Action channel"))
        }
    }

    private fun readCharacteristicAction(serviceUuid: String, characteristicUuid: String) {
        val serviceUuidAndroid = UUID.fromString("0000$serviceUuid-0000-1000-8000-00805f9b34fb")
        val characteristicUuidAndroid = UUID.fromString("0000$characteristicUuid-0000-1000-8000-00805f9b34fb")
        val characteristic: BluetoothGattCharacteristic? = gatt?.getService(serviceUuidAndroid)?.getCharacteristic(characteristicUuidAndroid)
        if (characteristic != null && gatt != null) {
            gatt!!.readCharacteristic(characteristic)
            Log.d(TAG, "Launched read Action")
        } else {
            Log.d(TAG, "Could not launch read Action: characteristic = $characteristic, gatt = $gatt")
            eventChannel.trySend(GattEvent.CharacteristicRead(Result.failure(RuntimeException("Could not start read characteristic action"))))
        }
    }

    override fun subscribeToCharacteristic(uuid: String): Flow<ByteArray> {
        TODO("Not yet implemented")
    }

    private val _isConnectedStateFlow = MutableStateFlow(false)
    override val isConnectedStateFlow: StateFlow<Boolean> = _isConnectedStateFlow

    private suspend inline fun <T> suspendCoroutineWithTimeout(
        timeout: Duration,
        crossinline block: (CancellableContinuation<T>) -> Unit
    ) = withTimeout(timeout) {
        suspendCancellableCoroutine(block)
    }
}