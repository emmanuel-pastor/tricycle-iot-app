package com.emmanuel.pastor.simplesmartapps.tricycle.data.remote.ble.models

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalUnsignedTypes::class)
class BleEntitiesTest {
    @Test
    fun `create BatteryBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0x50u)
        val batteryBleEntity = BatteryBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(80, batteryBleEntity?.percentage)
    }

    @Test
    fun `create LoadBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0xFFu, 0x01u)
        val loadBleEntity = LoadBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(511, loadBleEntity?.load)
    }

    @Test
    fun `create MileageBleEntity from unsigned byte array`() = runBlocking {
        val uByteArray = ubyteArrayOf(0x98u, 0x3au)
        val mileageBleEntity = MileageBleEntity.fromUByteArrayOrNull(uByteArray)
        assertEquals(15_000, mileageBleEntity?.mileage)
    }
}