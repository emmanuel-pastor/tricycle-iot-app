package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@SuppressWarnings("MissingPermission")
class BleManager @Inject constructor(adapter: BluetoothAdapter) {
    companion object {
        private const val TAG = "BleManager"
    }

    private val bluetoothLeScanner = adapter.bluetoothLeScanner

    private var scanCallback: ScanCallback? = null

    /**
     * Scans for a device with the given [address] and returns it if found.
     * @param address The address of the device to scan for.
     * @param timeout The time to wait for the device to be found.
     * @return The device if found, null otherwise.
     * @throws SecurityException if the app does not have the required permissions.
     */
    suspend fun scanForDevice(address: String, timeout: Duration = 10.seconds): BluetoothDevice? {
        return try {
            scanForDeviceSuspendWithTimeout(address, timeout)
        } catch (e: SecurityException) {
            throw e
        } catch (_: TimeoutCancellationException) {
            null
        } catch (e: Exception) {
            Log.e(TAG, "Error scanning for device", e)
            null
        } finally {
            try {
                bluetoothLeScanner.stopScan(scanCallback)
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping scan", e)
            }
        }
    }

    private suspend inline fun scanForDeviceSuspendWithTimeout(
        address: String,
        timeout: Duration = 10.seconds,
    ): BluetoothDevice? = suspendCoroutineWithTimeout(timeout) { continuation ->
        scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                if (result.device.address == address) {
                    continuation.resumeWith(Result.success(result.device))
                }
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                continuation.resumeWith(Result.failure(IllegalStateException("Scan failed with error code $errorCode")))
            }
        }
        bluetoothLeScanner.startScan(scanCallback)
    }
}

suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Duration,
    crossinline block: (CancellableContinuation<T>) -> Unit
) = withTimeout(timeout) {
    suspendCancellableCoroutine(block = block)
}