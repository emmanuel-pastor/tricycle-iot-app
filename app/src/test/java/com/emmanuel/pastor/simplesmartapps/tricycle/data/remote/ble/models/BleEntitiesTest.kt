package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BleEntitiesTest {
    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun `create BatteryBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0x50u)
        val batteryBleEntity = BatteryBleEntity.fromUByteArray(uByteArray)
        assertEquals(80, batteryBleEntity.percentage)
    }
}